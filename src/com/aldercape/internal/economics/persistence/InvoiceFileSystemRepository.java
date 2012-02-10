package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Invoice;
import com.aldercape.internal.economics.model.InvoiceBuilder;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.InvoiceEntryRepository;
import com.aldercape.internal.economics.model.InvoiceRepository;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.persistence.InvoiceFileSystemRepository.InvoiceJson;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementStorage;
import com.google.gson.reflect.TypeToken;

public class InvoiceFileSystemRepository implements ElementStorage<InvoiceJson>, InvoiceRepository {

	static class InvoiceJson {

		static class InvoiceItemJson {

			public InvoiceItemJson(InvoiceEntry entry, InvoiceEntryRepository invoiceEntryRepository) {
				InvoiceEntry e = entry;
				title = e.title();
				description = e.description();
				rate = e.rate();
				units = e.units();
				id = invoiceEntryRepository.getIdFor(e);
			}

			public InvoiceItemJson(String title, String description, Unit units, Rate rate, long id) {
				this.title = title;
				this.description = description;
				this.units = units;
				this.rate = rate;
				this.id = id;
			}

			private String title;
			private String description;
			private Unit units;
			private Rate rate;
			private long id;

			public InvoiceEntry asInvoiceEntry(InvoiceEntryRepository invoiceEntryRepository) {
				return invoiceEntryRepository.getById(id);
			}

			public String getTitle() {
				return title;
			}

			public String getDescription() {
				return description;
			}

			public Unit getUnits() {
				return units;
			}

			public Rate getRate() {
				return rate;
			}

		}

		private volatile Invoice invoice;
		private Client company;
		private Client client;
		private Day issueDate;
		private Day dueDate;
		private List<InvoiceItemJson> entries = new ArrayList<>();

		public InvoiceJson(Client company, Client client, Day issueDate, Day dueDate, List<InvoiceItemJson> entries) {
			this.company = company;
			this.client = client;
			this.issueDate = issueDate;
			this.dueDate = dueDate;
			this.entries = entries;
		}

		public InvoiceJson(Invoice invoice, InvoiceEntryRepository invoiceEntryRepository) {
			company = invoice.company();
			client = invoice.client();
			issueDate = invoice.issueDate();
			dueDate = invoice.dueDate();
			List<Entry<Day>> items = invoice.entries();
			for (Entry<Day> entry : items) {
				entries.add(new InvoiceItemJson((InvoiceEntry) entry, invoiceEntryRepository));
			}
		}

		public Invoice asInvoice(InvoiceEntryRepository invoiceEntryRepository) {
			if (invoice == null) {
				InvoiceBuilder invoiceBuilder = new InvoiceBuilder(company);
				invoiceBuilder.forClient(client);
				invoiceBuilder.issued(issueDate);
				invoiceBuilder.dueDate(dueDate);
				for (InvoiceItemJson entry : entries) {
					invoiceBuilder.addEntry(entry.asInvoiceEntry(invoiceEntryRepository));
				}
				invoice = invoiceBuilder.create();
			}
			return invoice;
		}
	}

	private JsonStorage<InvoiceJson> jsonStorage;
	private List<Invoice> entries = new ArrayList<>();
	private InvoiceEntryRepository invoiceEntryRepository;

	public InvoiceFileSystemRepository(File invoiceFile, InvoiceEntryRepository invoiceEntryRepository) {
		this.invoiceEntryRepository = invoiceEntryRepository;
		jsonStorage = new JsonStorage<InvoiceJson>(invoiceFile, false, this);
		jsonStorage.populateCache(new TypeToken<Map<Long, InvoiceJson>>() {
		});
	}

	public List<Invoice> getAll() {
		return entries;
	}

	public void add(Invoice invoice) {
		jsonStorage.addToStorage(new InvoiceJson(invoice, invoiceEntryRepository));
		jsonStorage.writeAllToFile();
	}

	@Override
	public void addWithoutCache(InvoiceJson t) {
		entries.add(t.asInvoice(invoiceEntryRepository));
	}

	@Override
	public boolean isSame(InvoiceJson value, InvoiceJson ref) {
		// TODO Auto-generated method stub
		return false;
	}

}

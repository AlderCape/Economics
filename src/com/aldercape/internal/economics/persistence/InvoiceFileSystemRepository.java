package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aldercape.internal.economics.model.Invoice;
import com.aldercape.internal.economics.model.InvoiceEntryRepository;
import com.aldercape.internal.economics.model.InvoiceRepository;
import com.aldercape.internal.economics.model.TimeEntryRepository;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementStorage;
import com.google.gson.reflect.TypeToken;

public class InvoiceFileSystemRepository implements ElementStorage<Invoice>, InvoiceRepository {

	private JsonStorage<Invoice> jsonStorage;
	private List<Invoice> entries = new ArrayList<>();
	private InvoiceEntryRepository invoiceEntryRepository;

	public InvoiceFileSystemRepository(File invoiceFile, InvoiceEntryRepository invoiceEntryRepository, TimeEntryRepository timeEntryRepository) {
		this.invoiceEntryRepository = invoiceEntryRepository;
		jsonStorage = new JsonStorage<Invoice>(invoiceFile, false, this, timeEntryRepository);
		jsonStorage.populateCache(new TypeToken<Map<Long, Invoice>>() {
		});
	}

	public List<Invoice> getAll() {
		return entries;
	}

	public void add(Invoice invoice) {
		jsonStorage.addToStorage(invoice);
		jsonStorage.writeAllToFile();
	}

	@Override
	public void addWithoutCache(Invoice t) {
		entries.add(t);
	}

	@Override
	public boolean isSame(Invoice value, Invoice ref) {
		// TODO Auto-generated method stub
		return false;
	}

}

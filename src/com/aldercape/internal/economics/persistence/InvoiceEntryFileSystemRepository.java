package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.aldercape.internal.economics.model.ComposedInvoiceEntry;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.InvoiceEntryGroupingRule;
import com.aldercape.internal.economics.model.InvoiceEntryRepository;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.TimeEntryRepository;
import com.aldercape.internal.economics.persistence.InvoiceEntryFileSystemRepository.InvoiceEntryJson;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementParser;

public class InvoiceEntryFileSystemRepository implements ElementParser<InvoiceEntryJson>, InvoiceEntryRepository {

	static class InvoiceEntryJson {
		private Set<Long> timeEntries = new LinkedHashSet<Long>();

		public InvoiceEntryJson(InvoiceEntry entry, TimeEntryRepository timeEntryRepository) {
			timeEntries = timeEntryRepository.getIdsFor(entry.getAllEntries());
		}

		public InvoiceEntryJson(Set<Long> timeEntries) {
			this.timeEntries = timeEntries;
		}

		public InvoiceEntry asInvoiceEntry(TimeEntryRepository timeEntryRepository) {
			Set<TimeEntry> entries = timeEntryRepository.findByIds(timeEntries);
			ComposedInvoiceEntry result = new ComposedInvoiceEntry(new InvoiceEntryGroupingRule(), entries);

			return result;
		}

	}

	private List<InvoiceEntry> entries = new ArrayList<InvoiceEntry>();
	private JsonStorage<InvoiceEntryJson> jsonStorage;
	private TimeEntryRepository timeEntryRepository;

	public InvoiceEntryFileSystemRepository(File invoiceEntryFile, TimeEntryRepository timeEntryRepository) {
		this.timeEntryRepository = timeEntryRepository;
		jsonStorage = new JsonStorage<InvoiceEntryJson>(invoiceEntryFile, false, this, "invoiceEntry");
		jsonStorage.populateCache();
	}

	public List<InvoiceEntry> getAll() {
		return entries;
	}

	public void add(InvoiceEntry entry) {
		jsonStorage.addToStorage(new InvoiceEntryJson(entry, timeEntryRepository));
		jsonStorage.writeAllToFile();
	}

	@Override
	public void addWithoutCache(InvoiceEntryJson t) {
		entries.add(t.asInvoiceEntry(timeEntryRepository));
	}

	@Override
	public boolean isSame(InvoiceEntryJson value, InvoiceEntryJson ref) {
		return value.timeEntries.equals(ref.timeEntries);
	}

	public long getIdFor(InvoiceEntry e) {
		return jsonStorage.getIdFor(new InvoiceEntryJson(e, timeEntryRepository));
	}

	public InvoiceEntry getById(long id) {
		return jsonStorage.getById(id).asInvoiceEntry(timeEntryRepository);
	}

}

package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.InvoiceEntryGroupingRule;
import com.aldercape.internal.economics.persistence.InvoiceEntryFileSystemRepository.InvoiceEntryJson;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class InvoiceEntryFileSystemRepository implements ElementParser<InvoiceEntryJson> {

	static class InvoiceEntryJson {

		private Set<Long> timeEntries = new LinkedHashSet<Long>();

		public InvoiceEntryJson(InvoiceEntry entry, TimeEntryFileSystemRepository timeEntryRepository) {
			timeEntries = timeEntryRepository.getIdsFor(entry.getAllEntries());
		}

		public InvoiceEntryJson(Set<Long> timeEntries) {
			this.timeEntries = timeEntries;
		}

		public InvoiceEntry asInvoiceEntry(TimeEntryFileSystemRepository timeEntryRepository) {
			return new InvoiceEntry(new InvoiceEntryGroupingRule(), timeEntryRepository.getById(timeEntries.iterator().next()));
		}

	}

	private List<InvoiceEntry> entries = new ArrayList<InvoiceEntry>();
	private JsonStorage<InvoiceEntryJson> jsonStorage;
	private TimeEntryFileSystemRepository timeEntryRepository;

	public InvoiceEntryFileSystemRepository(File invoiceEntryFile, TimeEntryFileSystemRepository timeEntryRepository) {
		this.timeEntryRepository = timeEntryRepository;
		jsonStorage = new JsonStorage<InvoiceEntryJson>(invoiceEntryFile, false, this);
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
	public InvoiceEntryJson deserialize(JsonElement entry) {
		Set<Long> ids = new LinkedHashSet<Long>();
		JsonObject jsonObject = entry.getAsJsonObject();
		JsonArray asJsonArray = jsonObject.get("timeEntries").getAsJsonArray();
		ids.add(asJsonArray.get(0).getAsLong());
		return new InvoiceEntryJson(ids);
	}

	@Override
	public void addWithoutCache(InvoiceEntryJson t) {
		entries.add(t.asInvoiceEntry(timeEntryRepository));
	}

	@Override
	public boolean isSame(InvoiceEntryJson value, InvoiceEntryJson ref) {
		// TODO Auto-generated method stub
		return false;
	}

}

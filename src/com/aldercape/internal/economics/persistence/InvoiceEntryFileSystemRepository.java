package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aldercape.internal.economics.model.BaseRepository;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.InvoiceEntryRepository;
import com.aldercape.internal.economics.model.TimeEntryRepository;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementStorage;
import com.google.gson.reflect.TypeToken;

public class InvoiceEntryFileSystemRepository implements ElementStorage<InvoiceEntry>, InvoiceEntryRepository, BaseRepository<InvoiceEntry> {

	private List<InvoiceEntry> entries = new ArrayList<InvoiceEntry>();
	private JsonStorage<InvoiceEntry> jsonStorage;
	private TimeEntryRepository timeEntryRepository;

	public InvoiceEntryFileSystemRepository(File invoiceEntryFile, RepositoryRegistry repositoryRegistry) {
		this.timeEntryRepository = repositoryRegistry.getRepository(TimeEntryRepository.class);
		jsonStorage = new JsonStorage<>(invoiceEntryFile, false, this, repositoryRegistry, new TypeToken<Map<Long, InvoiceEntry>>() {
		});
		jsonStorage.populateCache();
	}

	@Override
	public List<InvoiceEntry> getAll() {
		return entries;
	}

	@Override
	public void add(InvoiceEntry entry) {
		jsonStorage.addToStorage(entry);
		jsonStorage.writeAllToFile();
	}

	@Override
	public void addWithoutCache(InvoiceEntry t) {
		entries.add(t);
	}

	@Override
	public boolean isSame(InvoiceEntry value, InvoiceEntry ref) {
		return timeEntryRepository.getIdsFor(value.getAllEntries()).equals(timeEntryRepository.getIdsFor(ref.getAllEntries()));
	}

	@Override
	public long getIdFor(InvoiceEntry e) {
		return jsonStorage.getIdFor(e);
	}

	@Override
	public InvoiceEntry getById(long id) {
		return jsonStorage.getById(id);
	}

	@Override
	public void addListener(com.aldercape.internal.economics.model.BaseRepository.Listener listener) {

	}

}

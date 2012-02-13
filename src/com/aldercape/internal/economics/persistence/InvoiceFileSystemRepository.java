package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aldercape.internal.economics.model.BaseRepository;
import com.aldercape.internal.economics.model.Invoice;
import com.aldercape.internal.economics.model.InvoiceRepository;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementStorage;
import com.google.gson.reflect.TypeToken;

public class InvoiceFileSystemRepository implements ElementStorage<Invoice>, InvoiceRepository, BaseRepository<Invoice> {

	private JsonStorage<Invoice> jsonStorage;
	private List<Invoice> entries = new ArrayList<>();

	public InvoiceFileSystemRepository(File invoiceFile, RepositoryRegistry repositoryRegistry) {
		jsonStorage = new JsonStorage<Invoice>(invoiceFile, false, this, repositoryRegistry, new TypeToken<Map<Long, Invoice>>() {
		});
		jsonStorage.populateCache();
	}

	@Override
	public List<Invoice> getAll() {
		return entries;
	}

	@Override
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

	@Override
	public void addListener(com.aldercape.internal.economics.model.BaseRepository.Listener listener) {
		// TODO Auto-generated method stub

	}

}

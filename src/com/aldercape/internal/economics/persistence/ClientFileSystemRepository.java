package com.aldercape.internal.economics.persistence;

import java.io.File;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementParser;

public class ClientFileSystemRepository extends InMemoryClientRepository implements ClientRepository, ElementParser<Client> {

	private JsonStorage<Client> storage;

	public ClientFileSystemRepository(File storageFile) {
		this(storageFile, false);
	}

	public ClientFileSystemRepository(String fileName) {
		this(new File(fileName), true);
	}

	public ClientFileSystemRepository(File storageFile, boolean prettyPrinting) {
		storage = new JsonStorage<Client>(storageFile, prettyPrinting, this, "client");
		storage.populateCache();
	}

	@Override
	public void addWithoutCache(Client client) {
		super.add(client);
	}

	@Override
	public void add(Client client) {
		storage.addToStorage(client);
		storage.writeAllToFile();
		addWithoutCache(client);
	}

	public long getIdFor(Client client) {
		return storage.getIdFor(client);
	}

	@Override
	public boolean isSame(Client value, Client ref) {
		return value.name().equals(ref.name());
	}

	public Client getById(long client) {
		return storage.getById(client);
	}

}

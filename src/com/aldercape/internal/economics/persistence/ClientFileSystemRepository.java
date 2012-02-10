package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.util.Map;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementStorage;
import com.google.gson.reflect.TypeToken;

public class ClientFileSystemRepository extends InMemoryClientRepository implements ClientRepository, ElementStorage<Client> {

	private JsonStorage<Client> storage;

	public ClientFileSystemRepository(File storageFile) {
		this(storageFile, false);
	}

	public ClientFileSystemRepository(String fileName) {
		this(new File(fileName), true);
	}

	public ClientFileSystemRepository(File storageFile, boolean prettyPrinting) {
		storage = new JsonStorage<Client>(storageFile, prettyPrinting, this);
		storage.populateCache(new TypeToken<Map<Long, Client>>() {
		});
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

	@Override
	public long getIdFor(Client client) {
		return storage.getIdFor(client);
	}

	@Override
	public boolean isSame(Client value, Client ref) {
		return value.name().equals(ref.name());
	}

	@Override
	public Client getById(long client) {
		return storage.getById(client);
	}

}

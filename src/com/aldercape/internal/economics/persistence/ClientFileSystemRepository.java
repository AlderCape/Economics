package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.util.Map.Entry;

import com.aldercape.internal.economics.model.Address;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ClientFileSystemRepository extends InMemoryClientRepository implements ClientRepository, ElementParser<Client> {

	private JsonStorage<Client> storage;

	public ClientFileSystemRepository(File storageFile) {
		this(storageFile, false);
	}

	public ClientFileSystemRepository(String fileName) {
		this(new File(fileName), true);
	}

	public ClientFileSystemRepository(File storageFile, boolean prettyPrinting) {
		storage = new JsonStorage<Client>(storageFile, prettyPrinting, this);
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

	@Override
	public Client deserialize(Entry<String, JsonElement> entry) {
		return deserializeClient(entry);
	}

	private Client deserializeClient(Entry<String, JsonElement> entry) {
		JsonObject client = entry.getValue().getAsJsonObject();
		String name = client.get("name").getAsString();
		String vatNumber = client.get("vatNumber").getAsString();
		String contactPerson = client.get("contactPerson").getAsString();
		return new Client(name, deserializeAddress(client), vatNumber, contactPerson);
	}

	private Address deserializeAddress(JsonObject client) {
		JsonObject jsonAddress = client.get("address").getAsJsonObject();
		String streetName = jsonAddress.get("streetName").getAsString();
		String streetNumber = jsonAddress.get("streetNumber").getAsString();
		String zipcode = jsonAddress.get("zipcode").getAsString();
		String city = jsonAddress.get("city").getAsString();
		Address address = new Address(streetName, streetNumber, zipcode, city);
		return address;
	}

}

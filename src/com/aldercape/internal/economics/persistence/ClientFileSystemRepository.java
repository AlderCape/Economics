package com.aldercape.internal.economics.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.aldercape.internal.economics.model.Address;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class ClientFileSystemRepository extends InMemoryClientRepository implements ClientRepository {

	private File storageFile;
	private Map<Long, Client> clients;
	private boolean prettyPrinting;

	public ClientFileSystemRepository(File storageFile) {
		this.storageFile = storageFile;
		clients = new HashMap<Long, Client>();
		if (storageFile.length() != 0) {
			try {
				JsonParser jsonParser = new JsonParser();
				JsonElement parseResult = jsonParser.parse(new FileReader(storageFile));
				Set<Entry<String, JsonElement>> entrySet = parseResult.getAsJsonObject().entrySet();
				for (Entry<String, JsonElement> entry : entrySet) {
					long id = Long.parseLong(entry.getKey());
					Client client = deserializeClient(entry);
					clients.put(id, client);
					super.add(client);
				}
			} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
				new RuntimeException(e);
			}
		}
	}

	public ClientFileSystemRepository(String fileName) {
		this(new File(fileName));
		prettyPrinting = true;
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

	@Override
	public void add(Client client) {
		clients.put(getNextId(), client);
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(storageFile));
			GsonBuilder gsonBuilder = new GsonBuilder();
			if (prettyPrinting) {
				gsonBuilder = gsonBuilder.setPrettyPrinting();
			}
			bufferedWriter.append(gsonBuilder.create().toJson(clients));
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		super.add(client);
	}

	private Long getNextId() {
		if (clients.isEmpty()) {
			return 1l;
		}
		return Collections.max(clients.keySet()) + 1;
	}

}

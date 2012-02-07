package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Address;
import com.aldercape.internal.economics.model.Client;
import com.google.gson.JsonObject;

public class ClientJsonDeserializer implements JsonModelDeserializer<Client> {

	private JsonModule jsonModule;

	public ClientJsonDeserializer(JsonModule jsonModule) {
		this.jsonModule = jsonModule;
	}

	@Override
	public Client deserialize(JsonObject client) {
		String name = client.get("name").getAsString();
		String vatNumber = client.get("vatNumber").getAsString();
		String contactPerson = client.get("contactPerson").getAsString();
		Address address = (Address) jsonModule.getDeserializer("address").deserialize(client.get("address").getAsJsonObject());
		return new Client(name, address, vatNumber, contactPerson);
	}

}

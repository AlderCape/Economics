package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;

import com.aldercape.internal.economics.model.Address;
import com.aldercape.internal.economics.model.Client;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ClientJsonDeserializer implements JsonDeserializer<Client> {

	@Override
	public Client deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		JsonObject client = arg0.getAsJsonObject();
		String name = client.get("name").getAsString();
		String vatNumber = client.get("vatNumber").getAsString();
		String contactPerson = client.get("contactPerson").getAsString();
		Address address = arg2.deserialize(client.get("address"), Address.class);
		return new Client(name, address, vatNumber, contactPerson);
	}

}

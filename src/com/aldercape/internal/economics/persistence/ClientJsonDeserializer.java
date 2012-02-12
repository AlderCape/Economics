package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;

import com.aldercape.internal.economics.model.Address;
import com.aldercape.internal.economics.model.Client;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ClientJsonDeserializer implements JsonDeserializer<Client>, JsonSerializer<Client> {

	@Override
	public Client deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		JsonObject client = arg0.getAsJsonObject();
		String name = client.get("name").getAsString();
		String vatNumber = client.get("vatNumber").getAsString();
		String contactPerson = client.get("contactPerson").getAsString();
		Address address = arg2.deserialize(client.get("address"), Address.class);
		return new Client(name, address, vatNumber, contactPerson);
	}

	@Override
	public JsonElement serialize(Client arg0, Type arg1, JsonSerializationContext arg2) {
		JsonObject result = new JsonObject();
		result.add("name", arg2.serialize(arg0.name()));
		result.add("vatNumber", arg2.serialize(arg0.vatNumber()));
		result.add("address", arg2.serialize(arg0.address()));
		result.add("contactPerson", arg2.serialize(arg0.contactPerson()));
		return result;
	}

}

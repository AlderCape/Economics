package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;

import com.aldercape.internal.economics.model.Address;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class AddressJsonDeserializer implements JsonDeserializer<Address> {

	@Override
	public Address deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		JsonObject jsonAddress = arg0.getAsJsonObject();
		String streetName = jsonAddress.get("streetName").getAsString();
		String streetNumber = jsonAddress.get("streetNumber").getAsString();
		String zipcode = jsonAddress.get("zipcode").getAsString();
		String city = jsonAddress.get("city").getAsString();
		return new Address(streetName, streetNumber, zipcode, city);
	}

}

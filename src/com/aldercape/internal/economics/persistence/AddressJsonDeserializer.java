package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Address;
import com.google.gson.JsonObject;

public class AddressJsonDeserializer implements JsonModelDeserializer<Address> {

	@Override
	public Address deserialize(JsonObject jsonAddress) {
		String streetName = jsonAddress.get("streetName").getAsString();
		String streetNumber = jsonAddress.get("streetNumber").getAsString();
		String zipcode = jsonAddress.get("zipcode").getAsString();
		String city = jsonAddress.get("city").getAsString();
		return new Address(streetName, streetNumber, zipcode, city);
	}

}

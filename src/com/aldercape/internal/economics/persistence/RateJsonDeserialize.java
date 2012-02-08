package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Rate;
import com.google.gson.JsonObject;

public class RateJsonDeserialize implements JsonModelDeserializer<Rate> {

	@Override
	public Rate deserialize(JsonObject jsonObj) {
		return Rate.daily(jsonObj.get("amount").getAsJsonObject().get("amount").getAsInt());
	}

}

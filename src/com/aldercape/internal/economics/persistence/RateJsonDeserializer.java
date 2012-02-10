package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;

import com.aldercape.internal.economics.model.Rate;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class RateJsonDeserializer implements JsonDeserializer<Rate> {

	@Override
	public Rate deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		return Rate.daily(arg0.getAsJsonObject().get("amount").getAsJsonObject().get("amount").getAsInt());
	}

}

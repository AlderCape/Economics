package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;

import com.aldercape.internal.economics.model.Unit;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class UnitJsonDeserializer implements JsonDeserializer<Unit> {

	@Override
	public Unit deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		return Unit.days(arg0.getAsJsonObject().get("amount").getAsInt());
	}

}
package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Unit;
import com.google.gson.JsonObject;

public class UnitJsonDeserializer implements JsonModelDeserializer<Unit> {

	@Override
	public Unit deserialize(JsonObject jsonObj) {
		return Unit.days(jsonObj.get("amount").getAsInt());
	}

}
package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.MonthLiteral;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class DayJsonDeserializer implements JsonDeserializer<Day> {

	@Override
	public Day deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		JsonObject jsonObj = arg0.getAsJsonObject();
		int day = jsonObj.get("day").getAsInt();
		String month = jsonObj.get("month").getAsJsonObject().get("month").getAsString();
		int year = jsonObj.get("month").getAsJsonObject().get("year").getAsInt();
		return Day.createFrom(MonthLiteral.valueOf(month), day, year);
	}
}

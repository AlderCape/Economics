package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.MonthLiteral;
import com.google.gson.JsonObject;

public class DayJsonDeserializer implements JsonModelDeserializer<Day> {

	@Override
	public Day deserialize(JsonObject jsonObj) {
		int day = jsonObj.get("day").getAsInt();
		String month = jsonObj.get("month").getAsJsonObject().get("month").getAsString();
		int year = jsonObj.get("month").getAsJsonObject().get("year").getAsInt();
		return Day.createFrom(MonthLiteral.valueOf(month), day, year);
	}

}

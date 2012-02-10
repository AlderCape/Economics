package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.persistence.TimeEntryFileSystemRepository.TimeEntryJson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class TimeEntryJsonDeserializer implements JsonDeserializer<TimeEntryJson> {

	@Override
	public TimeEntryJson deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		JsonObject timeEntryJson = arg0.getAsJsonObject();
		long collaboratorId = timeEntryJson.get("collaborator").getAsLong();
		long clientId = timeEntryJson.get("client").getAsLong();
		Rate rate = arg2.deserialize(timeEntryJson.get("rate"), Rate.class);
		Unit unit = arg2.deserialize(timeEntryJson.get("unit"), Unit.class);
		Day day = arg2.deserialize(timeEntryJson.get("day"), Day.class);
		return new TimeEntryJson(collaboratorId, clientId, rate, unit, day);
	}

}

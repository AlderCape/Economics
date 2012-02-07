package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.persistence.TimeEntryFileSystemRepository.TimeEntryJson;
import com.google.gson.JsonObject;

public class TimeEntryJsonDeserializer implements JsonModelDeserializer<TimeEntryJson> {

	private JsonModule jsonModule;

	public TimeEntryJsonDeserializer(JsonModule jsonModule) {
		this.jsonModule = jsonModule;
	}

	@Override
	public TimeEntryJson deserialize(JsonObject timeEntryJson) {
		long collaboratorId = timeEntryJson.get("collaborator").getAsLong();
		long clientId = timeEntryJson.get("client").getAsLong();
		Rate rate = (Rate) jsonModule.getDeserializer("rate").deserialize(timeEntryJson.get("rate").getAsJsonObject());
		Unit unit = (Unit) jsonModule.getDeserializer("unit").deserialize(timeEntryJson.get("unit").getAsJsonObject());
		Day day = (Day) jsonModule.getDeserializer("day").deserialize(timeEntryJson.get("day").getAsJsonObject());
		return new TimeEntryJson(collaboratorId, clientId, rate, unit, day);
	}

}

package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.Unit;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TimeEntryJsonDeserializer implements JsonDeserializer<TimeEntry>, JsonSerializer<TimeEntry> {

	private CollaboratorRepository collaboratorRepository;
	private ClientRepository clientRepository;

	public TimeEntryJsonDeserializer(CollaboratorRepository collaboratorRepository, ClientRepository clientRepository) {
		this.collaboratorRepository = collaboratorRepository;
		this.clientRepository = clientRepository;
	}

	@Override
	public TimeEntry deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		JsonObject timeEntryJson = arg0.getAsJsonObject();
		long collaboratorId = timeEntryJson.get("collaboratorId").getAsLong();
		long clientId = timeEntryJson.get("clientId").getAsLong();
		Rate rate = arg2.deserialize(timeEntryJson.get("rate"), Rate.class);
		Unit unit = arg2.deserialize(timeEntryJson.get("unit"), Unit.class);
		Day day = arg2.deserialize(timeEntryJson.get("day"), Day.class);
		Collaborator collaborator = collaboratorRepository.getById(collaboratorId);
		Client client = clientRepository.getById(clientId);
		return new TimeEntry(unit, rate, collaborator, client, day);
	}

	@Override
	public JsonElement serialize(TimeEntry arg0, Type arg1, JsonSerializationContext arg2) {
		JsonObject result = new JsonObject();
		result.add("collaboratorId", arg2.serialize(collaboratorRepository.getIdFor(arg0.collaborator())));
		result.add("clientId", arg2.serialize(clientRepository.getIdFor(arg0.client())));
		result.add("rate", arg2.serialize(arg0.rate()));
		result.add("unit", arg2.serialize(arg0.units()));
		result.add("day", arg2.serialize(arg0.day()));
		return result;
	}
}

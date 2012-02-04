package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.MonthLiteral;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementParser;
import com.aldercape.internal.economics.persistence.TimeEntryFileSystemRepository.TimeEntryJson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TimeEntryFileSystemRepository implements ElementParser<TimeEntryJson> {

	static class TimeEntryJson {

		private long collaborator;
		private long client;
		private Rate rate;
		private Unit unit;
		private Day day;

		public TimeEntryJson(TimeEntry timeEntry, CollaboratorFileSystemRepository collaboratorRepository, ClientFileSystemRepository clientRepository) {
			this.unit = timeEntry.units();
			this.rate = timeEntry.rate();
			this.day = timeEntry.day();
			collaborator = collaboratorRepository.getIdFor(timeEntry.collaborator());
			client = clientRepository.getIdFor(timeEntry.client());
		}

		public TimeEntryJson(long collaboratorId, long clientId, Rate rate, Unit unit, Day day) {
			this.collaborator = collaboratorId;
			this.client = clientId;
			this.rate = rate;
			this.unit = unit;
			this.day = day;
		}

		public TimeEntry asTimeEntry(CollaboratorFileSystemRepository collaboratorRepository, ClientFileSystemRepository clientRepository) {
			return new TimeEntry(unit, rate, collaboratorRepository.getById(collaborator), clientRepository.getById(client), day);
		}
	}

	private JsonStorage<TimeEntryJson> jsonStorage;
	private CollaboratorFileSystemRepository collaboratorRepository;
	private ClientFileSystemRepository clientRepository;
	private List<TimeEntry> entries = new ArrayList<TimeEntry>();

	public TimeEntryFileSystemRepository(File newFile, CollaboratorFileSystemRepository collaboratorRepository, ClientFileSystemRepository clientRepository) {
		this.collaboratorRepository = collaboratorRepository;
		this.clientRepository = clientRepository;
		jsonStorage = new JsonStorage<TimeEntryJson>(newFile, false, this);
		jsonStorage.populateCache();
	}

	public List<TimeEntry> getAll() {
		return entries;
	}

	public void add(TimeEntry timeEntry) {
		jsonStorage.addToStorage(new TimeEntryJson(timeEntry, collaboratorRepository, clientRepository));
		jsonStorage.writeAllToFile();
	}

	@Override
	public TimeEntryJson deserialize(JsonElement entry) {
		JsonObject timeEntryJson = entry.getAsJsonObject();
		long collaboratorId = timeEntryJson.get("collaborator").getAsLong();
		long clientId = timeEntryJson.get("client").getAsLong();
		JsonObject rateJson = timeEntryJson.get("rate").getAsJsonObject();
		JsonObject unitJson = timeEntryJson.get("unit").getAsJsonObject();
		JsonObject dayJson = timeEntryJson.get("day").getAsJsonObject();
		return new TimeEntryJson(collaboratorId, clientId, deserializeRate(rateJson), deserializeUnit(unitJson), deserializeDay(dayJson));
	}

	private Day deserializeDay(JsonObject dayJson) {
		int day = dayJson.get("day").getAsInt();
		String month = dayJson.get("month").getAsJsonObject().get("month").getAsString();
		int year = dayJson.get("month").getAsJsonObject().get("year").getAsInt();
		return Day.createFrom(MonthLiteral.valueOf(month), day, year);
	}

	private Unit deserializeUnit(JsonObject unitJson) {
		return Unit.days(unitJson.get("amount").getAsInt());
	}

	private Rate deserializeRate(JsonObject rateJson) {
		return Rate.daily(rateJson.get("amount").getAsJsonObject().get("amount").getAsInt());
	}

	@Override
	public void addWithoutCache(TimeEntryJson t) {
		entries.add(t.asTimeEntry(collaboratorRepository, clientRepository));
	}

	@Override
	public boolean isSame(TimeEntryJson value, TimeEntryJson ref) {
		return value.unit.equals(ref.unit) && value.day.equals(ref.day) && value.rate.equals(ref.rate) && value.collaborator == ref.collaborator;
	}

	public TimeEntry getById(long i) {
		return jsonStorage.getById(i).asTimeEntry(collaboratorRepository, clientRepository);
	}

	public Set<Long> getIdsFor(Set<TimeEntry> allEntries) {
		Set<Long> result = new LinkedHashSet<Long>();
		for (TimeEntry e : allEntries) {
			result.add(jsonStorage.getIdFor(new TimeEntryJson(e, collaboratorRepository, clientRepository)));
		}
		return result;
	}
}

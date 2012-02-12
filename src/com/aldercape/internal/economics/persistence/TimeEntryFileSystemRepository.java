package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.TimeEntryRepository;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementStorage;
import com.google.gson.reflect.TypeToken;

public class TimeEntryFileSystemRepository implements ElementStorage<TimeEntry>, TimeEntryRepository {

	private JsonStorage<TimeEntry> jsonStorage;
	private CollaboratorRepository collaboratorRepository;
	private ClientRepository clientRepository;
	private List<TimeEntry> entries = new ArrayList<TimeEntry>();

	public TimeEntryFileSystemRepository(File newFile, CollaboratorRepository collaboratorRepository, ClientRepository clientRepository) {
		this.collaboratorRepository = collaboratorRepository;
		this.clientRepository = clientRepository;
		jsonStorage = new JsonStorage<TimeEntry>(newFile, false, this, this, clientRepository, collaboratorRepository);
		jsonStorage.populateCache(new TypeToken<Map<Long, TimeEntry>>() {
		});

	}

	public List<TimeEntry> getAll() {
		return entries;
	}

	@Override
	public void add(TimeEntry timeEntry) {
		jsonStorage.addToStorage(timeEntry);
		jsonStorage.writeAllToFile();
	}

	@Override
	public void addWithoutCache(TimeEntry t) {
		entries.add(t);
	}

	@Override
	public boolean isSame(TimeEntry value, TimeEntry ref) {
		return value.units().equals(ref.units()) && value.day().equals(ref.day()) && value.rate().equals(ref.rate()) && collaboratorRepository.getIdFor(value.collaborator()) == collaboratorRepository.getIdFor(ref.collaborator());
	}

	public TimeEntry getById(long i) {
		return jsonStorage.getById(i);
	}

	@Override
	public Set<Long> getIdsFor(Set<TimeEntry> allEntries) {
		Set<Long> result = new LinkedHashSet<Long>();
		for (TimeEntry e : allEntries) {
			result.add(jsonStorage.getIdFor(e));
		}
		return result;
	}

	@Override
	public Set<TimeEntry> findByIds(Set<Long> ids) {
		Set<TimeEntry> result = new LinkedHashSet<>();
		for (Long id : ids) {
			result.add(getById(id));
		}
		return result;
	}
}

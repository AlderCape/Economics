package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.util.Map;

import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementStorage;
import com.google.gson.reflect.TypeToken;

public class CollaboratorFileSystemRepository extends InMemoryCollaboratorRepository implements CollaboratorRepository, ElementStorage<Collaborator> {

	private JsonStorage<Collaborator> jsonStorage;

	public CollaboratorFileSystemRepository(File storageFile) {
		this(storageFile, false);
	}

	public CollaboratorFileSystemRepository(String fileName) {
		this(new File(fileName), true);
	}

	private CollaboratorFileSystemRepository(File storageFile, boolean prettyPrinting) {
		jsonStorage = new JsonStorage<Collaborator>(storageFile, false, this, new RepositoryRegistry(), new TypeToken<Map<Long, Collaborator>>() {
		});
		jsonStorage.populateCache();
	}

	@Override
	public void add(Collaborator collaborator) {
		jsonStorage.addToStorage(collaborator);
		jsonStorage.writeAllToFile();
		super.add(collaborator);
	}

	@Override
	public void addWithoutCache(Collaborator collaborator) {
		super.add(collaborator);
	}

	@Override
	public long getIdFor(Collaborator collaborator) {
		return jsonStorage.getIdFor(collaborator);
	}

	@Override
	public boolean isSame(Collaborator value, Collaborator ref) {
		return value.compareTo(ref) == 0;
	}

	@Override
	public Collaborator getById(long collaborator) {
		return jsonStorage.getById(collaborator);
	}

}

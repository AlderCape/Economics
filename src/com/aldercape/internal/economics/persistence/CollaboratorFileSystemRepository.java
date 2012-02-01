package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.util.Map.Entry;

import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CollaboratorFileSystemRepository extends InMemoryCollaboratorRepository implements CollaboratorRepository, ElementParser<Collaborator> {

	private JsonStorage<Collaborator> jsonStorage;

	public CollaboratorFileSystemRepository(File storageFile) {
		this(storageFile, false);
	}

	public CollaboratorFileSystemRepository(String fileName) {
		this(new File(fileName), true);
	}

	private CollaboratorFileSystemRepository(File storageFile, boolean prettyPrinting) {
		jsonStorage = new JsonStorage<Collaborator>(storageFile, false, this);
		jsonStorage.populateCache();
	}

	@Override
	public Collaborator deserialize(Entry<String, JsonElement> entry) {
		JsonObject collaborator = entry.getValue().getAsJsonObject();
		String firstname = collaborator.get("firstname").getAsString();
		String lastname = collaborator.get("lastname").getAsString();
		String email = collaborator.get("email").getAsString();
		return new Collaborator(firstname, lastname, email);
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

}

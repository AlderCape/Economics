package com.aldercape.internal.economics.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.aldercape.internal.economics.model.BaseRepository;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.NoMatchingRecordException;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class CollaboratorFileSystemRepository implements CollaboratorRepository {

	private Map<Long, Collaborator> collaborators = new HashMap<Long, Collaborator>();
	private File storageFile;
	private boolean prettyPrinting;

	public CollaboratorFileSystemRepository(File storageFile) {
		this.storageFile = storageFile;
		if (storageFile.length() != 0) {
			try {
				JsonParser jsonParser = new JsonParser();
				JsonElement parseResult = jsonParser.parse(new FileReader(storageFile));
				Set<Entry<String, JsonElement>> entrySet = parseResult.getAsJsonObject().entrySet();
				for (Entry<String, JsonElement> entry : entrySet) {
					long id = Long.parseLong(entry.getKey());
					Collaborator client = deserializeCollaborator(entry);
					collaborators.put(id, client);
				}
			} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
				new RuntimeException(e);
			}
		}

	}

	private Collaborator deserializeCollaborator(Entry<String, JsonElement> entry) {
		JsonObject collaborator = entry.getValue().getAsJsonObject();
		String firstname = collaborator.get("firstname").getAsString();
		String lastname = collaborator.get("lastname").getAsString();
		String email = collaborator.get("email").getAsString();
		return new Collaborator(firstname, lastname, email);
	}

	@Override
	public List<Collaborator> getAll() {
		return new ArrayList<Collaborator>(collaborators.values());
	}

	@Override
	public void addListener(BaseRepository.Listener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(Collaborator client) {
		collaborators.put(getNextId(), client);
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(storageFile));
			GsonBuilder gsonBuilder = new GsonBuilder();
			if (prettyPrinting) {
				gsonBuilder = gsonBuilder.setPrettyPrinting();
			}
			bufferedWriter.append(gsonBuilder.create().toJson(collaborators));
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Collaborator findByEmail(String email) {
		for (Collaborator collaborator : collaborators.values()) {
			if (collaborator.email().equals(email)) {
				return collaborator;
			}
		}
		throw new NoMatchingRecordException("No collaborator with the email " + email);
	}

	private long getNextId() {
		if (collaborators.isEmpty()) {
			return 1;
		}
		return Collections.max(collaborators.keySet()) + 1;
	}

}

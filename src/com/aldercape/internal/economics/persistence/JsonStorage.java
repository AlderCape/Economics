package com.aldercape.internal.economics.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.TimeEntryRepository;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class JsonStorage<T> {

	public interface ElementStorage<T> {

		public void addWithoutCache(T t);

		public boolean isSame(T value, T ref);
	}

	private File storageFile;
	boolean prettyPrinting;
	private Map<Long, T> values;
	private ElementStorage<T> parser;
	JsonModule module;
	private TypeToken<?> token;

	public JsonStorage(File storageFile, boolean prettyPrinting, ElementStorage<T> elementParser, TimeEntryRepository timeEntryRepository, ClientRepository clientRepository, CollaboratorRepository collaboratorRepository, TypeToken<?> token) {
		this.storageFile = storageFile;
		this.prettyPrinting = prettyPrinting;
		this.parser = elementParser;
		this.module = new JsonModule(timeEntryRepository, clientRepository, collaboratorRepository);
		this.token = token;
	}

	public JsonStorage(File storageFile, boolean prettyPrinting, ElementStorage<T> elementParser, TimeEntryRepository timeEntryRepository, TypeToken<?> token) {
		this(storageFile, prettyPrinting, elementParser, timeEntryRepository, null, null, token);
	}

	public void writeAllToFile() {
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(storageFile));
			bufferedWriter.append(module.createJsonEngine(prettyPrinting).toJson(values, token.getType()));
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

	public void populateCache() {
		values = new HashMap<Long, T>();
		if (storageFile.length() != 0) {
			try {

				values = module.createJsonEngine(prettyPrinting).fromJson(new FileReader(storageFile), token.getType());
				for (T value : values.values()) {
					parser.addWithoutCache(value);
				}
			} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
				new RuntimeException(e);
			}
		}
	}

	public void addToStorage(T client) {
		values.put(getNextId(), client);
	}

	public long getIdFor(T t) {
		Set<Entry<Long, T>> entrySet = values.entrySet();
		for (Entry<Long, T> entry : entrySet) {
			if (parser.isSame(entry.getValue(), t)) {
				return entry.getKey();
			}
		}
		throw new RuntimeException("Id not found for " + t);
	}

	public T getById(long key) {
		return values.get(key);
	}

	private Long getNextId() {
		if (values.isEmpty()) {
			return 1l;
		}
		return Collections.max(values.keySet()) + 1;
	}

}

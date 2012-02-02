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

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonStorage<T> {

	public interface ElementParser<T> {
		public T deserialize(Entry<String, JsonElement> entry);

		public void addWithoutCache(T t);

		public boolean isSame(T value, T ref);
	}

	private File storageFile;
	private boolean prettyPrinting;
	Map<Long, T> values;
	private ElementParser<T> parser;

	public JsonStorage(File storageFile, boolean prettyPrinting, ElementParser<T> elementParser) {
		this.storageFile = storageFile;
		this.prettyPrinting = prettyPrinting;
		this.parser = elementParser;
	}

	public void writeAllToFile() {
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(storageFile));
			GsonBuilder gsonBuilder = new GsonBuilder();
			if (prettyPrinting) {
				gsonBuilder = gsonBuilder.setPrettyPrinting();
			}
			bufferedWriter.append(gsonBuilder.create().toJson(values));
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

	public void addToStorage(T client) {
		values.put(getNextId(), client);
	}

	public void populateCache() {
		values = new HashMap<Long, T>();
		if (storageFile.length() != 0) {
			try {
				JsonElement parseResult = parseJson();
				addEntries(parseResult);
			} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
				new RuntimeException(e);
			}
		}
	}

	private void addEntries(JsonElement parseResult) {
		for (Entry<String, JsonElement> entry : parseResult.getAsJsonObject().entrySet()) {
			T client = addToCache(parser, entry);
			parser.addWithoutCache(client);
		}
	}

	private JsonElement parseJson() throws FileNotFoundException {
		JsonParser jsonParser = new JsonParser();
		JsonElement parseResult = jsonParser.parse(new FileReader(storageFile));
		return parseResult;
	}

	private long getId(Entry<String, JsonElement> entry) {
		long id = Long.parseLong(entry.getKey());
		return id;
	}

	private T addToCache(ElementParser<T> parser, Entry<String, JsonElement> entry) {
		long id = getId(entry);
		T client = parser.deserialize(entry);
		values.put(id, client);
		return client;
	}

	private Long getNextId() {
		if (values.isEmpty()) {
			return 1l;
		}
		return Collections.max(values.keySet()) + 1;
	}

	public long getIdFor(T t) {
		Set<Entry<Long, T>> entrySet = values.entrySet();
		for (Entry<Long, T> entry : entrySet) {
			if (parser.isSame(entry.getValue(), t)) {
				return entry.getKey();
			}
		}
		return -1;
	}

	public T getById(long key) {
		return values.get(key);
	}
}

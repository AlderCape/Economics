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
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class JsonStorage<T> {

	private JsonModule module;

	public interface ElementStorage<T> {

		public void addWithoutCache(T t);

		public boolean isSame(T value, T ref);
	}

	private File storageFile;
	private boolean prettyPrinting;
	private Map<Long, T> values;
	private ElementStorage<T> parser;

	public JsonStorage(File storageFile, boolean prettyPrinting, ElementStorage<T> elementParser) {
		this.storageFile = storageFile;
		this.prettyPrinting = prettyPrinting;
		this.parser = elementParser;
		this.module = new JsonModule();
	}

	public void writeAllToFile() {
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(storageFile));
			GsonBuilder gsonBuilder = new GsonBuilder();
			module.regiterOn(gsonBuilder);
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

	public void populateCache(TypeToken<?> token) {
		values = new HashMap<Long, T>();
		if (storageFile.length() != 0) {
			try {
				GsonBuilder gsonBuilder = new GsonBuilder();
				module.regiterOn(gsonBuilder);

				values = gsonBuilder.create().fromJson(new FileReader(storageFile), token.getType());

				for (T value : values.values()) {
					parser.addWithoutCache(value);
				}
			} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
				new RuntimeException(e);
			}
		}
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
		throw new RuntimeException("Id not found for " + t);
	}

	public T getById(long key) {
		return values.get(key);
	}

}

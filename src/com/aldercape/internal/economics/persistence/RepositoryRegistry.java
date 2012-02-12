package com.aldercape.internal.economics.persistence;

import java.util.HashMap;
import java.util.Map;

public class RepositoryRegistry {

	private Map<Class<?>, Object> repositories = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T> T getRepository(Class<T> klass) {
		return (T) repositories.get(klass);
	}

	public void setRepository(Class<?> klass, Object repository) {
		repositories.put(klass, repository);
	}

}

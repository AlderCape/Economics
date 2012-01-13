package com.aldercape.internal.economics.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class InMemoryRepository<T> {

	private List<T> colaborators = new ArrayList<T>();

	public List<T> getAll() {
		return Collections.unmodifiableList(colaborators);
	}

	public void add(T toAdd) {
		colaborators.add(toAdd);
		notifyListeners();
	}

	public void notifyListeners() {

	}

}

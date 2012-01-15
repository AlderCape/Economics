package com.aldercape.internal.economics.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.aldercape.internal.economics.model.BaseRepository;

public abstract class InMemoryRepository<T> implements BaseRepository<T> {

	private List<T> colaborators = new ArrayList<T>();
	private List<Listener> listeners = new ArrayList<Listener>();

	@Override
	public List<T> getAll() {
		return Collections.unmodifiableList(colaborators);
	}

	public void add(T toAdd) {
		colaborators.add(toAdd);
		notifyListeners();
	}

	@Override
	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public void notifyListeners() {
		for (Listener listener : listeners) {
			listener.changed();
		}
	}

}

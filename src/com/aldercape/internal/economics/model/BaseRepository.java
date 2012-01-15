package com.aldercape.internal.economics.model;

import java.util.List;

public interface BaseRepository<T> {

	public List<T> getAll();

	public void addListener(Listener listener);

	public interface Listener {
		public void changed();
	}
}

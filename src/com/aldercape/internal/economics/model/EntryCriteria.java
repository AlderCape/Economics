package com.aldercape.internal.economics.model;

public interface EntryCriteria<T extends TimePoint> {

	public boolean matches(Entry<T> matchingEntry);

}

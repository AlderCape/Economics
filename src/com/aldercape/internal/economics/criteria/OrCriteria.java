package com.aldercape.internal.economics.criteria;

import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.EntryCriteria;
import com.aldercape.internal.economics.model.TimePoint;

public class OrCriteria<T extends TimePoint> extends AbstractEntryCriteria<T> {

	private EntryCriteria<T> first;
	private EntryCriteria<T> second;

	public OrCriteria(EntryCriteria<T> first, EntryCriteria<T> second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public boolean matches(Entry<T> matchingEntry) {
		return first.matches(matchingEntry) || second.matches(matchingEntry);
	}

}

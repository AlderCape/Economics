package com.aldercape.internal.economics.criteria;

import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.EntryCriteria;
import com.aldercape.internal.economics.model.TimePoint;

public class AndCriteria<T extends TimePoint> extends AbstractEntryCriteria<T> {

	private EntryCriteria<T> first;
	private EntryCriteria<T> second;

	public AndCriteria(EntryCriteria<T> first, EntryCriteria<T> second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public boolean matches(Entry<T> other) {
		return first.matches(other) && second.matches(other);
	}

}

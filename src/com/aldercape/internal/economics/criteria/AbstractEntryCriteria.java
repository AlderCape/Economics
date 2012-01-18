package com.aldercape.internal.economics.criteria;

import com.aldercape.internal.economics.model.EntryCriteria;
import com.aldercape.internal.economics.model.TimePoint;

public abstract class AbstractEntryCriteria<T extends TimePoint> implements EntryCriteria<T> {

	public EntryCriteria<T> and(EntryCriteria<T> other) {
		return new AndCriteria<T>(this, other);
	}

}

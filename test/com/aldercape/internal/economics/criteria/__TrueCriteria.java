package com.aldercape.internal.economics.criteria;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.EntryCriteria;

public final class __TrueCriteria implements EntryCriteria<Day> {
	@Override
	public boolean matches(Entry<Day> matchingEntry) {
		return true;
	}

	@Override
	public EntryCriteria<Day> and(EntryCriteria<Day> other) {
		return this;
	}
}
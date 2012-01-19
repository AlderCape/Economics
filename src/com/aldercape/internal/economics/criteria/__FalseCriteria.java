package com.aldercape.internal.economics.criteria;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.EntryCriteria;

public final class __FalseCriteria implements EntryCriteria<Day> {
	@Override
	public boolean matches(Entry<Day> matchingEntry) {
		return false;
	}

	@Override
	public EntryCriteria<Day> and(EntryCriteria<Day> other) {
		return this;
	}
}
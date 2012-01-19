package com.aldercape.internal.economics.criteria;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Euro;

public class RateCriteria<T> extends AbstractEntryCriteria<Day> {

	private Euro rate;

	public RateCriteria(Euro rate) {
		this.rate = rate;
	}

	@Override
	public boolean matches(Entry<Day> matchingEntry) {
		return rate.equals(matchingEntry.rate());
	}

}

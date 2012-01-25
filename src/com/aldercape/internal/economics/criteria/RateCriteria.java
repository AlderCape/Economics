package com.aldercape.internal.economics.criteria;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Rate;

public class RateCriteria<T> extends AbstractEntryCriteria<Day> {

	private Rate rate;

	public RateCriteria(Rate rate) {
		this.rate = rate;
	}

	@Override
	public boolean matches(Entry<Day> matchingEntry) {
		return rate.equals(matchingEntry.rate());
	}

}

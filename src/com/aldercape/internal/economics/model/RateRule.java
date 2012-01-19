package com.aldercape.internal.economics.model;

import java.util.Comparator;

import com.aldercape.internal.economics.criteria.RateCriteria;

public class RateRule implements Rule<Entry<Day>> {

	public EntryCriteria<Day> getCriteria(Euro rate) {
		return new RateCriteria<Day>(rate);
	}

	public EntryCriteria<Day> getCriteria(Entry<Day> example) {
		return getCriteria(example.rate());
	}

	public Comparator<Entry<Day>> getComparator() {
		return new Comparator<Entry<Day>>() {
			@Override
			public int compare(Entry<Day> o1, Entry<Day> o2) {
				return o1.rate().compareTo(o2.rate());
			}
		};
	}

}

package com.aldercape.internal.economics.model;

import java.util.Comparator;

import com.aldercape.internal.economics.criteria.AbstractEntryCriteria;
import com.aldercape.internal.economics.criteria.MonthCriteria;

public class MonthRule implements Rule<Entry<Day>> {

	public AbstractEntryCriteria<Day> getCriteria(Day timePoint) {
		return new MonthCriteria<Day>(timePoint);
	}

	@Override
	public AbstractEntryCriteria<Day> getCriteria(Entry<Day> example) {
		return getCriteria(example.getTimePoint());
	}

	@Override
	public Comparator<Entry<Day>> getComparator() {
		return new Comparator<Entry<Day>>() {
			@Override
			public int compare(Entry<Day> o1, Entry<Day> o2) {
				return o1.getTimePoint().compareTo(o2.getTimePoint());
			}
		};
	}

}

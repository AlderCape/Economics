package com.aldercape.internal.economics.criteria;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Month;
import com.aldercape.internal.economics.model.TimePoint;

public class MonthCriteria<T extends TimePoint> extends AbstractEntryCriteria<T> {

	private Month month;

	public MonthCriteria(Month month) {
		this.month = month;
	}

	public MonthCriteria(Day day) {
		this(day.month());
	}

	@Override
	public boolean matches(Entry<T> matchingEntry) {
		return month.equals(matchingEntry.getTimePoint().month());
	}

}

package com.aldercape.internal.economics.criteria;

import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.TimePoint;

public class TimePointCriteria<T extends TimePoint> extends AbstractEntryCriteria<T> {

	private T timePoint;

	public TimePointCriteria(T timePoint) {
		this.timePoint = timePoint;
	}

	@Override
	public boolean matches(Entry<T> matchingEntry) {
		return timePoint.equals(matchingEntry.getTimePoint());
	}

}

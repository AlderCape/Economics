package com.aldercape.internal.economics.criteria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aldercape.internal.economics.model.Day;

public class TimePointCriteriaTest {

	@Test
	public void matches() {
		TimePointCriteria<Day> criteria = new TimePointCriteria<Day>(Day.january(1, 2012));
		EntryFake entry = new EntryFake();
		entry.timePoint = Day.january(1, 2012);
		assertTrue(criteria.matches(entry));
	}

	@Test
	public void noMatches() {
		TimePointCriteria<Day> criteria = new TimePointCriteria<Day>(Day.january(1, 2012));
		EntryFake entry = new EntryFake();
		entry.timePoint = Day.january(2, 2012);
		assertFalse(criteria.matches(entry));
		entry.timePoint = Day.december(31, 2011);
		assertFalse(criteria.matches(entry));
	}
}

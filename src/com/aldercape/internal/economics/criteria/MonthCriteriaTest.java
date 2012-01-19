package com.aldercape.internal.economics.criteria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Month;

public class MonthCriteriaTest {

	@Test
	public void matches() {
		AbstractEntryCriteria<Day> criteria = new MonthCriteria<Day>(Month.april(2012));
		EntryFake entry = new EntryFake();
		entry.timePoint = Day.april(1, 2012);
		assertTrue(criteria.matches(entry));

		entry.timePoint = Day.april(30, 2012);
		assertTrue(criteria.matches(entry));
	}

	@Test
	public void noMatch() {
		AbstractEntryCriteria<Day> criteria = new MonthCriteria<Day>(Month.april(2012));
		EntryFake entry = new EntryFake();
		entry.timePoint = Day.mars(31, 2012);
		assertFalse(criteria.matches(entry));

		entry.timePoint = Day.may(1, 2012);
		assertFalse(criteria.matches(entry));
	}

}

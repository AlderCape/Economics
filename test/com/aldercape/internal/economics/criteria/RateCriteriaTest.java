package com.aldercape.internal.economics.criteria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Rate;

public class RateCriteriaTest {
	@Test
	public void matches() {
		AbstractEntryCriteria<Day> criteria = new RateCriteria<Day>(Rate.daily(new Euro(100)));
		EntryFake entry = new EntryFake();
		entry.rate = new Euro(100);
		assertTrue(criteria.matches(entry));
	}

	@Test
	public void noMatch() {
		AbstractEntryCriteria<Day> criteria = new RateCriteria<Day>(Rate.daily(new Euro(100)));
		EntryFake entry = new EntryFake();
		entry.rate = new Euro(99);
		assertFalse(criteria.matches(entry));

		entry.rate = new Euro(101);
		assertFalse(criteria.matches(entry));
	}

}

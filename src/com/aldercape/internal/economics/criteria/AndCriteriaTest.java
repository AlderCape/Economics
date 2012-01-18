package com.aldercape.internal.economics.criteria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.EntryCriteria;

public class AndCriteriaTest {

	private EntryCriteria<Day> trueCriteria;
	private EntryCriteria<Day> falseCriteria;

	@Before
	public void setUp() {
		trueCriteria = new EntryCriteria<Day>() {

			@Override
			public boolean matches(Entry<Day> matchingEntry) {
				return true;
			}
		};
		falseCriteria = new EntryCriteria<Day>() {

			@Override
			public boolean matches(Entry<Day> matchingEntry) {
				return false;
			}
		};
	}

	@Test
	public void matches() {
		AndCriteria<Day> criteria = new AndCriteria<Day>(trueCriteria, trueCriteria);
		assertTrue(criteria.matches(new EntryFake()));
	}

	@Test
	public void noMatch() {
		assertFalse(new AndCriteria<Day>(falseCriteria, falseCriteria).matches(new EntryFake()));
		assertFalse(new AndCriteria<Day>(trueCriteria, falseCriteria).matches(new EntryFake()));
		assertFalse(new AndCriteria<Day>(falseCriteria, trueCriteria).matches(new EntryFake()));
	}
}

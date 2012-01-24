package com.aldercape.internal.economics.criteria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.EntryCriteria;

public class AndCriteriaTest {

	private EntryCriteria<Day> trueCriteria;
	private EntryCriteria<Day> falseCriteria;

	@Before
	public void setUp() {
		trueCriteria = new __TrueCriteria();
		falseCriteria = new __FalseCriteria();
	}

	@Test
	public void matches() {
		assertTrue(new AndCriteria<Day>(trueCriteria, trueCriteria).matches(new EntryFake()));
	}

	@Test
	public void noMatch() {
		assertFalse(new AndCriteria<Day>(falseCriteria, falseCriteria).matches(new EntryFake()));
		assertFalse(new AndCriteria<Day>(trueCriteria, falseCriteria).matches(new EntryFake()));
		assertFalse(new AndCriteria<Day>(falseCriteria, trueCriteria).matches(new EntryFake()));
	}
}

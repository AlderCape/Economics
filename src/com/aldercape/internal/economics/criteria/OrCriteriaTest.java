package com.aldercape.internal.economics.criteria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.EntryCriteria;

public class OrCriteriaTest {

	private EntryCriteria<Day> trueCriteria;
	private EntryCriteria<Day> falseCriteria;

	@Before
	public void setUp() {
		trueCriteria = new __TrueCriteria();
		falseCriteria = new __FalseCriteria();
	}

	@Test
	public void matches() {
		assertTrue(new OrCriteria<Day>(trueCriteria, trueCriteria).matches(new EntryFake()));
		assertTrue(new OrCriteria<Day>(trueCriteria, falseCriteria).matches(new EntryFake()));
		assertTrue(new OrCriteria<Day>(falseCriteria, trueCriteria).matches(new EntryFake()));
	}

	@Test
	public void noMatch() {
		assertFalse(new OrCriteria<Day>(falseCriteria, falseCriteria).matches(new EntryFake()));
	}
}

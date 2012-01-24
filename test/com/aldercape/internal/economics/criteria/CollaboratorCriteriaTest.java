package com.aldercape.internal.economics.criteria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class CollaboratorCriteriaTest {

	private CollaboratorCriteria<Day> criteria;

	@Before
	public void setUp() {
		criteria = new CollaboratorCriteria<Day>(new __TestObjectMother().me());
	}

	@Test
	public void matches() {
		assertTrue(criteria.matches(new __TestObjectMother().me()));
		EntryFake entry = new EntryFake();
		entry.collaborator = new __TestObjectMother().me();
		assertTrue(criteria.matches(entry));
	}

	@Test
	public void noMatch() {
		assertFalse(criteria.matches(new __TestObjectMother().other()));
		EntryFake entry = new EntryFake();
		entry.collaborator = new __TestObjectMother().other();
		assertFalse(criteria.matches(entry));
	}

}

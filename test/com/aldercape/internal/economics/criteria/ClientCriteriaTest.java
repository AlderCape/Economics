package com.aldercape.internal.economics.criteria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class ClientCriteriaTest {

	private ClientCriteria<Day> clientCriteria;

	@Before
	public void setUp() {
		clientCriteria = new ClientCriteria<Day>(new __TestObjectMother().myCompany());
	}

	@Test
	public void matches() {
		assertTrue(clientCriteria.matches(new __TestObjectMother().myCompany()));
		EntryFake entry = new EntryFake();
		entry.client = new __TestObjectMother().myCompany();
		assertTrue(clientCriteria.matches(entry));
	}

	@Test
	public void noMatch() {
		assertFalse(clientCriteria.matches(new __TestObjectMother().otherCompany()));
		EntryFake entry = new EntryFake();
		entry.client = new __TestObjectMother().otherCompany();
		assertFalse(clientCriteria.matches(entry));
	}
}

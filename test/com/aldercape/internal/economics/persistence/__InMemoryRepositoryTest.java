package com.aldercape.internal.economics.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ui.__TestObjectMother;

public abstract class __InMemoryRepositoryTest<T> {

	private InMemoryRepository<T> repository;
	protected __TestObjectMother objectMother;
	private List<T> expectedList;

	@Before
	public void setUp() {
		repository = getRepository();
		objectMother = new __TestObjectMother();
		expectedList = new ArrayList<T>();
	}

	protected abstract InMemoryRepository<T> getRepository();

	protected abstract T getSecondEntry();

	protected abstract T getFirstEntry();

	@Test
	public void shouldBeEmptyOnCreation() {
		assertEquals(expectedList, repository.getAll());
	}

	@Test
	public void shouldHaveEntriesWhenAdded() {
		repository.add(getFirstEntry());
		expectedList.add(getFirstEntry());
		assertEquals(expectedList, repository.getAll());

		repository.add(getSecondEntry());
		expectedList.add(getSecondEntry());
		assertEquals(expectedList, repository.getAll());
	}

	@Test
	public void shouldNotBePossibleToAddToTheReturnedList() {
		List<T> all = repository.getAll();
		try {
			all.add(getFirstEntry());
			fail();
		} catch (UnsupportedOperationException e) {
		}
	}

}

package com.aldercape.internal.economics.persistence;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.NoMatchingRecordException;

public class InMemoryCollaboratorRepositoryTest extends __InMemoryRepositoryTest<Collaborator> {

	private InMemoryCollaboratorRepository repository;

	@Override
	@Before
	public void setUp() {
		this.repository = new InMemoryCollaboratorRepository();
		super.setUp();
	}

	@Override
	protected InMemoryRepository<Collaborator> getRepository() {
		return new InMemoryCollaboratorRepository();
	}

	@Override
	protected Collaborator getSecondEntry() {
		return objectMother.other();
	}

	@Override
	protected Collaborator getFirstEntry() {
		return objectMother.me();
	}

	@Test(expected = NoMatchingRecordException.class)
	public void shouldThrowExceptionIfCollaboratorIsNotPresentOnGet() {
		repository.findByEmail("No such collaborator");
	}

}

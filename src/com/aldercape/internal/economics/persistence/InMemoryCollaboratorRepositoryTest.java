package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Collaborator;

public class InMemoryCollaboratorRepositoryTest extends __InMemoryRepositoryTest<Collaborator> {

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
}

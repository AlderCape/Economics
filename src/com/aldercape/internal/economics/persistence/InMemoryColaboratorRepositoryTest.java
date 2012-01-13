package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Colaborator;

public class InMemoryColaboratorRepositoryTest extends __InMemoryRepositoryTest<Colaborator> {

	@Override
	protected InMemoryRepository<Colaborator> getRepository() {
		return new InMemoryColaboratorRepository();
	}

	@Override
	protected Colaborator getSecondEntry() {
		return objectMother.other();
	}

	@Override
	protected Colaborator getFirstEntry() {
		return objectMother.me();
	}
}

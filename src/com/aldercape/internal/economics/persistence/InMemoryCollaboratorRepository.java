package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.CollaboratorRepository;

public class InMemoryCollaboratorRepository extends InMemoryRepository<Collaborator> implements CollaboratorRepository {

	@Override
	public Collaborator findByEmail(String email) {
		return getAll().get(0);
	}

}

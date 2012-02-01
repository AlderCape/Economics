package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.NoMatchingRecordException;

public class InMemoryCollaboratorRepository extends InMemoryRepository<Collaborator> implements CollaboratorRepository {

	@Override
	public Collaborator findByEmail(String email) {
		for (Collaborator collaborator : getAll()) {
			if (collaborator.email().equals(email)) {
				return collaborator;
			}
		}
		throw new NoMatchingRecordException("No collaborator found with wmail " + email);
	}

}

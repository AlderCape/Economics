package com.aldercape.internal.economics.criteria;

import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.TimePoint;

public class CollaboratorCriteria<T extends TimePoint> extends AbstractEntryCriteria<T> {

	private Collaborator collaborator;

	public CollaboratorCriteria(Collaborator collaborator) {
		this.collaborator = collaborator;
	}

	public boolean matches(Collaborator other) {
		return collaborator.fullname().equals(other.fullname());
	}

	@Override
	public boolean matches(Entry<T> other) {
		return matches(other.collaborator());
	}

}

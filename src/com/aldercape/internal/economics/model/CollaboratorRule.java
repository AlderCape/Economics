package com.aldercape.internal.economics.model;

import java.util.Comparator;

import com.aldercape.internal.economics.criteria.CollaboratorCriteria;

public class CollaboratorRule implements Rule<Entry<Day>> {

	public EntryCriteria<Day> getCriteria(Collaborator collaborator) {
		return new CollaboratorCriteria<Day>(collaborator);
	}

	@Override
	public EntryCriteria<Day> getCriteria(Entry<Day> example) {
		return getCriteria(example.collaborator());
	}

	@Override
	public Comparator<Entry<Day>> getComparator() {
		return new Comparator<Entry<Day>>() {
			@Override
			public int compare(Entry<Day> o1, Entry<Day> o2) {
				return o1.collaborator().compareTo(o2.collaborator());
			}
		};
	}

}

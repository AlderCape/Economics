package com.aldercape.internal.economics.model;

import java.util.Comparator;

import com.aldercape.internal.economics.criteria.ClientCriteria;

public class ClientRule implements Rule<Entry<Day>> {

	public EntryCriteria<Day> getCriteria(Client client) {
		return new ClientCriteria<Day>(client);
	}

	@Override
	public EntryCriteria<Day> getCriteria(Entry<Day> example) {
		return getCriteria(example.client());
	}

	@Override
	public Comparator<Entry<Day>> getComparator() {
		return new Comparator<Entry<Day>>() {

			@Override
			public int compare(Entry<Day> o1, Entry<Day> o2) {
				return o1.client().name().compareTo(o2.client().name());
			}
		};
	}

}

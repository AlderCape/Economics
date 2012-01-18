package com.aldercape.internal.economics.criteria;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.TimePoint;

public class ClientCriteria<T extends TimePoint> extends AbstractEntryCriteria<T> {

	private Client client;

	public ClientCriteria(Client client) {
		this.client = client;
	}

	public boolean matches(Client other) {
		return client.name().equals(other.name());
	}

	@Override
	public boolean matches(Entry<T> other) {
		return matches(other.client());
	}

}

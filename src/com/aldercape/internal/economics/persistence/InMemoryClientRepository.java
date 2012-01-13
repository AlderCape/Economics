package com.aldercape.internal.economics.persistence;

import java.util.ArrayList;
import java.util.List;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;

public class InMemoryClientRepository extends InMemoryRepository<Client> implements ClientRepository {

	private List<Listener> listeners = new ArrayList<Listener>();

	@Override
	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	@Override
	public void notifyListeners() {
		for (Listener listener : listeners) {
			listener.changed();
		}
	}

}

package com.aldercape.internal.economics.persistence;

import java.util.List;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.NoMatchingRecordException;

public class InMemoryClientRepository extends InMemoryRepository<Client> implements ClientRepository {

	@Override
	public Client getByName(String name) throws NoMatchingRecordException {
		List<Client> all = getAll();
		for (Client client : all) {
			if (client.name().equals(name)) {
				return client;
			}
		}
		throw new NoMatchingRecordException("No client with the name " + name);
	}

	@Override
	public Client getById(long client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getIdFor(Client client) {
		// TODO Auto-generated method stub
		return 0;
	}

}

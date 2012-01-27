package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;

public class InMemoryClientRepository extends InMemoryRepository<Client> implements ClientRepository {

	@Override
	public Client findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}

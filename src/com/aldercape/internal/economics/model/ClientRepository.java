package com.aldercape.internal.economics.model;

import java.util.List;

public interface ClientRepository {

	public List<Client> getAll();

	public void add(Client myCompany);

	public interface Listener {

		void changed();

	}

	public void addListener(Listener listener);
}

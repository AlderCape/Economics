package com.aldercape.internal.economics.model;

public interface ClientRepository extends BaseRepository<Client> {

	public void add(Client myCompany);

	public Client getByName(String name);

}

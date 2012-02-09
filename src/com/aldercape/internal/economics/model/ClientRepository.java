package com.aldercape.internal.economics.model;

public interface ClientRepository extends BaseRepository<Client> {

	public void add(Client client);

	public Client getByName(String name) throws NoMatchingRecordException;

	public Client getById(long client);

	public long getIdFor(Client client);

}

package com.aldercape.internal.economics.model;

public interface CollaboratorRepository extends BaseRepository<Collaborator> {

	public void add(Collaborator client);

	public Collaborator findByEmail(String email);

	public Collaborator getById(long collaborator);

	public long getIdFor(Collaborator collaborator);

}

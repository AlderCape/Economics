package com.aldercape.internal.economics.model;

public class ProjectMember {

	private Collaborator collaborator;
	private Rate rate;

	public ProjectMember(Collaborator collaborator, Rate rate) {
		this.collaborator = collaborator;
		this.rate = rate;
	}

	public Collaborator getCollaborator() {
		return collaborator;
	}

	public Rate rate() {
		return rate;
	}

}

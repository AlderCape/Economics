package com.aldercape.internal.economics.model;

public class Project {

	private String name;
	private Client client;
	private int memberCount;

	public Project(String name, Client client) {
		this.name = name;
		this.client = client;
	}

	public String name() {
		return name;
	}

	public Client client() {
		return client;
	}

	public void addMember(Collaborator member, Rate rate) {
		memberCount++;
	}

	public int memberCount() {
		return memberCount;
	}

}

package com.aldercape.internal.economics.model;

import java.util.ArrayList;
import java.util.List;

public class Project {

	private String name;
	private Client client;
	private List<ProjectMember> members = new ArrayList<>();

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
		members.add(new ProjectMember(member, rate));
	}

	public int memberCount() {
		return members.size();
	}

	public ProjectMember getMember(int i) {
		return members.get(i);
	}

	public List<ProjectMember> members() {
		return members;
	}

}

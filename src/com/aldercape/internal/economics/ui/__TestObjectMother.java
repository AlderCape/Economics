package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Collaborator;

public class __TestObjectMother {

	private Client myCompany = new Client("My Company");
	private Client otherCompany = new Client("Other Company");
	private Collaborator me = new Collaborator("Me");
	private Collaborator other = new Collaborator("Other");

	public Client otherCompany() {
		return otherCompany;
	}

	public Client myCompany() {
		return myCompany;
	}

	public Collaborator me() {
		return me;
	}

	public Collaborator other() {
		return other;
	}
}

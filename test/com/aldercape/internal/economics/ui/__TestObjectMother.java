package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.Address;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Collaborator;

public class __TestObjectMother {

	private Client myCompany = new Client("My Company", new Address("Sesame Street", "1", "12345", "My City"), "0123456789", "My contact");
	private Client otherCompany = new Client("Other Company", new Address("Other street", "5", "54321", "Other City"), "9876543210", "Other contact");
	private Collaborator me = new Collaborator("Me", "Surname", "me@mycompany.com");
	private Collaborator other = new Collaborator("Other", "Something", "other@othercompany.com");

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

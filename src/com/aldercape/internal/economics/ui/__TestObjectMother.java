package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Colaborator;

public class __TestObjectMother {

	private Client myCompany = new Client("My Company");
	private Client otherCompany = new Client("Other Company");
	private Colaborator me = new Colaborator("Me");
	private Colaborator other = new Colaborator("Other");

	public Client otherCompany() {
		return otherCompany;
	}

	public Client myCompany() {
		return myCompany;
	}

	public Colaborator me() {
		return me;
	}

	public Colaborator other() {
		return other;
	}
}

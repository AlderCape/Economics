package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Colaborator;

public class __TestObjectMother {

	private Client myCompany = new Client("My Company");
	private Colaborator me = new Colaborator("Me");

	public Client myCompany() {
		return myCompany;
	}

	public Colaborator me() {
		return me;
	}
}

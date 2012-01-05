package com.aldercape.internal.economics.model;

public class Entry {

	private Colaborator colaborator;
	private Client client;
	private Euro rate;
	private Unit units;

	public Entry(Unit units, Euro rate, Colaborator colaborator, Client client) {
		this.units = units;
		this.rate = rate;
		this.colaborator = colaborator;
		this.client = client;
	}

	public Client client() {
		return client;
	}

	public Colaborator colaborator() {
		return colaborator;
	}

	public Euro rate() {
		return rate;
	}

	public Unit units() {
		return units;
	}

	public Euro amount() {
		return rate.times(units.days());
	}

	public Euro vat() {
		return amount().percentage(21);
	}

}

package com.aldercape.internal.economics.model;

public class Entry<T extends TimePoint> { // TODO should become an interface

	private Colaborator colaborator;
	private Client client;
	private Euro rate;
	private Unit units;
	private T time;

	public Entry(Unit units, Euro rate, Colaborator colaborator, Client client, T time) {
		this.units = units;
		this.rate = rate;
		this.colaborator = colaborator;
		this.client = client;
		this.time = time;
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
		return rate().times(units().days());
	}

	public Euro vat() {
		return amount().percentage(21);
	}

	public T getTimePoint() {
		return time;
	}

}

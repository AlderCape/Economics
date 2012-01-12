package com.aldercape.internal.economics.model;

public class AbstractEntry<T extends TimePoint> implements Entry<T> {

	private Colaborator colaborator;
	private Client client;
	private Euro rate;
	private Unit units;
	private T time;

	public AbstractEntry(Unit units, Euro rate, Colaborator colaborator, Client client, T time) {
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

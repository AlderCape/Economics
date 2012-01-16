package com.aldercape.internal.economics.model;

public class AbstractEntry<T extends TimePoint> implements Entry<T> {

	private Collaborator collaborator;
	private Client client;
	private Euro rate;
	private Unit units;
	private T time;

	public AbstractEntry(Unit units, Euro rate, Collaborator collaborator, Client client, T time) {
		this.units = units;
		this.rate = rate;
		this.collaborator = collaborator;
		this.client = client;
		this.time = time;
	}

	public Client client() {
		return client;
	}

	public Collaborator collaborator() {
		return collaborator;
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

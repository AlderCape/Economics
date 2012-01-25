package com.aldercape.internal.economics.model;

public class AbstractEntry<T extends TimePoint> implements Entry<T> {

	private Collaborator collaborator;
	private Client client;
	private Rate rate;
	private Unit units;
	private T time;

	public AbstractEntry(Unit units, Rate rate, Collaborator collaborator, Client client, T time) {
		this.units = units;
		this.rate = rate;
		this.collaborator = collaborator;
		this.client = client;
		this.time = time;
	}

	@Override
	public Client client() {
		return client;
	}

	@Override
	public Collaborator collaborator() {
		return collaborator;
	}

	@Override
	public Unit units() {
		return units;
	}

	@Override
	public Euro amount() {
		return rate().costPerDay().times(units().days());
	}

	@Override
	public Euro vat() {
		return amount().percentage(21);
	}

	@Override
	public T getTimePoint() {
		return time;
	}

	@Override
	public Rate rate() {
		return rate;
	}

}

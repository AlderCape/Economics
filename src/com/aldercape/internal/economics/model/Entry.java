package com.aldercape.internal.economics.model;

public interface Entry<T extends TimePoint> {

	public Euro amount();

	public T getTimePoint();

	public Collaborator collaborator();

	public Client client();

	public Unit units();

	public Euro costPerDay();

	public Euro vat();

	public Rate rate();

}

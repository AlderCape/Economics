package com.aldercape.internal.economics.model;

public interface Entry<T extends TimePoint> {

	public Euro amount();

	public T getTimePoint();

	public Colaborator colaborator();

	public Client client();

	public Unit units();

	public Euro rate();

	public Euro vat();

}

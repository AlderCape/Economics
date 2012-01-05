package com.aldercape.internal.economics.model;

public class TimeEntry extends Entry {

	private Day day;

	public TimeEntry(Unit unit, Euro rate, Colaborator colaborator, Client client, Day day) {
		super(unit, rate, colaborator, client);
		this.day = day;
	}

	public Day day() {
		return day;
	}

	public Month month() {
		return day().month();
	}

}

package com.aldercape.internal.economics.model;

public class TimeEntry extends AbstractEntry<Day> {

	public TimeEntry(Unit unit, Euro rate, Colaborator colaborator, Client client, Day day) {
		super(unit, rate, colaborator, client, day);
	}

	public Day day() {
		return getTimePoint();
	}

	public Month month() {
		return day().month();
	}

}

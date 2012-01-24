package com.aldercape.internal.economics.model;

public class TimeEntry extends AbstractEntry<Day> {

	public TimeEntry(Unit unit, Rate rate, Collaborator collaborator, Client client, Day day) {
		super(unit, rate, collaborator, client, day);
	}

	public Day day() {
		return getTimePoint();
	}

	public Month month() {
		return day().month();
	}

}

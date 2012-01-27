package com.aldercape.internal.economics.google.calendar;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.Unit;
import com.google.api.services.calendar.model.Event;

public class CalendarEntry implements Entry<Day> {

	private Entry<Day> entry;

	public CalendarEntry(Event base, CollaboratorRepository repository, ClientRepository clientRepository) {
		this.entry = new EntryBuilder(repository, clientRepository).create(base);
	}

	@Override
	public Euro amount() {
		return entry.amount();
	}

	@Override
	public Day getTimePoint() {
		return entry.getTimePoint();
	}

	@Override
	public Collaborator collaborator() {
		return entry.collaborator();
	}

	@Override
	public Client client() {
		return entry.client();
	}

	@Override
	public Unit units() {
		return entry.units();
	}

	@Override
	public Euro vat() {
		return entry.vat();
	}

	@Override
	public Rate rate() {
		return entry.rate();
	}
}

package com.aldercape.internal.economics.google.calendar;

import java.util.Date;
import java.util.List;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.Unit;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;

public class EntryBuilder {

	private Event base;
	private CollaboratorRepository collaboratorRepository;
	private ClientRepository clientRepository;

	public EntryBuilder(CollaboratorRepository repository, ClientRepository clientRepository) {
		this.collaboratorRepository = repository;
		this.clientRepository = clientRepository;
	}

	public Entry<Day> create(Event base) {
		this.base = base;
		return new TimeEntry(units(), rate(), collaborator(), client(), getTimePoint());
	}

	private Unit units() {
		if (isWholeDay()) {
			return Unit.days(1);
		} else {
			int numberOfHours = numberOfHours();
			return Unit.days(numberOfHours / 8);
		}
	}

	private boolean isWholeDay() {
		return numberOfHours() == 0;
	}

	@SuppressWarnings("deprecation")
	private int numberOfHours() {
		return new Date(base.getEnd().getDateTime().getValue()).getHours() - new Date(base.getStart().getDateTime().getValue()).getHours();
	}

	private Rate rate() {
		return Rate.daily(Integer.parseInt((String) base.getUnknownKeys().get("rate")));
	}

	private Collaborator collaborator() {
		List<EventAttendee> attendees = base.getAttendees();
		return collaboratorRepository.findByEmail(attendees.get(0).getEmail());
	}

	private Client client() {
		return clientRepository.getByName((String) base.getUnknownKeys().get("client"));
	}

	private Day getTimePoint() {
		return Day.fromLong(base.getStart().getDateTime().getValue());
	}

}

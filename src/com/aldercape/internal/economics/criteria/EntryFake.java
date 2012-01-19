package com.aldercape.internal.economics.criteria;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Unit;

public final class EntryFake implements Entry<Day> {

	public Client client;
	public Collaborator collaborator;
	public Day timePoint;
	public Euro rate;

	@Override
	public Euro amount() {
		return null;
	}

	@Override
	public Day getTimePoint() {
		return timePoint;
	}

	@Override
	public Collaborator collaborator() {
		return collaborator;
	}

	@Override
	public Client client() {
		return client;
	}

	@Override
	public Unit units() {
		return null;
	}

	@Override
	public Euro rate() {
		return rate;
	}

	@Override
	public Euro vat() {
		return null;
	}
}
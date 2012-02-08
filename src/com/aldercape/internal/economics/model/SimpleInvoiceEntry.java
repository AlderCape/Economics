package com.aldercape.internal.economics.model;

import java.util.Collections;
import java.util.Set;

public class SimpleInvoiceEntry extends AbstractEntry<Day> implements InvoiceEntry {

	public SimpleInvoiceEntry(Unit units, Rate rate, Collaborator person, Client client, Day issueDate) {
		super(units, rate, person, client, issueDate);
	}

	public Month bookkeepingMonth() {
		return getTimePoint().month();
	}

	public Day issueDate() {
		return getTimePoint();
	}

	public SimpleInvoiceEntry addTime(Unit units) {
		return new SimpleInvoiceEntry(units().plus(units), rate(), collaborator(), client(), issueDate());
	}

	@Override
	public Set<TimeEntry> getAllEntries() {
		return Collections.singleton(new TimeEntry(units(), rate(), collaborator(), client(), getTimePoint()));
	}

	public String title() {
		return null;
	}

	public String description() {
		return null;
	}

}

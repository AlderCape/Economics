package com.aldercape.internal.economics.model;

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

}

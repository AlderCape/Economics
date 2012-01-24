package com.aldercape.internal.economics.model;

public class SimpleInvoiceEntry extends AbstractEntry<Day> implements InvoiceEntry {

	public SimpleInvoiceEntry(Unit units, Euro rate, Collaborator person, Client client, Day issueDate) {
		super(units, Rate.daily(rate), person, client, issueDate);
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

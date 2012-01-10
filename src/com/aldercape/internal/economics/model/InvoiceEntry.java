package com.aldercape.internal.economics.model;

public class InvoiceEntry extends Entry<Day> {

	public InvoiceEntry(Unit units, Euro rate, Colaborator person, Client client, Day issueDate) {
		super(units, rate, person, client, issueDate);
	}

	public Month bookkeepingMonth() {
		return getTimePoint().month();
	}

	public Day issueDate() {
		return getTimePoint();
	}

	public InvoiceEntry addTime(Unit units) {
		return new InvoiceEntry(units().plus(units), rate(), colaborator(), client(), issueDate());
	}

}

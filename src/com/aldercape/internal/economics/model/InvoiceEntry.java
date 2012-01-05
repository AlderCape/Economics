package com.aldercape.internal.economics.model;

public class InvoiceEntry extends Entry {

	private Month bookkeepingMonth;
	private Month cashflowMonth;

	public InvoiceEntry(Unit units, Euro rate, Colaborator person, Client client, Month bookkeepingMonth, Month cashflowMonth) {
		super(units, rate, person, client);
		this.bookkeepingMonth = bookkeepingMonth;
		this.cashflowMonth = cashflowMonth;
	}

	public Month bookkeepingMonth() {
		return bookkeepingMonth;
	}

	public Month cashflowMonth() {
		return cashflowMonth;
	}

	public InvoiceEntry addTime(Unit units) {
		return new InvoiceEntry(units().plus(units), rate(), colaborator(), client(), bookkeepingMonth(), cashflowMonth());
	}

}

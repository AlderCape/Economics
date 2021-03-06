package com.aldercape.internal.economics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvoiceBuilder {

	private Client company;
	private Client client;
	private List<InvoiceEntry> entries = new ArrayList<>();
	private int daysToPay;
	private Day issueDay;
	private boolean failOnIntegretyChecks = true;

	public InvoiceBuilder(Client company) {
		this.company = company;
	}

	public InvoiceBuilder forClient(Client client) {
		this.client = client;
		List<InvoiceEntry> newEntries = new ArrayList<>();
		for (InvoiceEntry entry : entries) {
			if (isValid(entry)) {
				newEntries.add(entry);
			}
		}
		entries = newEntries;
		return this;
	}

	public InvoiceBuilder addEntry(InvoiceEntry entry) {
		entries.add(entry);
		return this;

	}

	public boolean isValid() {
		return company != null && client != null && !entries.isEmpty() && daysToPay >= 0 && issueDay != null;
	}

	public InvoiceBuilder daysToPay(int daysToPay) {
		this.daysToPay = daysToPay;
		return this;
	}

	public InvoiceBuilder with(int with) {
		return daysToPay(with);
	}

	public InvoiceBuilder daysToPay() {
		return this;
	}

	public InvoiceBuilder andEntry(InvoiceEntry entry) {
		if (isValid(entry)) {
			addEntry(entry);
		}
		return this;
	}

	public boolean isValid(Entry<Day> entry) {
		boolean isValid = true;
		if (client != null) {
			if (failOnIntegretyChecks) {
				Invoice.assertIsForClient(entry, client);
			} else {
				isValid = Invoice.isValid(entry, client);
			}
		}
		return isValid;
	}

	public InvoiceBuilder issueDay(Day issueDay) {
		this.issueDay = issueDay;
		return this;
	}

	public Invoice create() {
		return new Invoice(company, client, issueDay, entries, daysToPay);
	}

	public InvoiceBuilder issued(Day day) {
		return issueDay(day);
	}

	public void filterInvalidEntries() {
		failOnIntegretyChecks = false;
	}

	public List<InvoiceEntry> entries() {
		return Collections.unmodifiableList(entries);
	}

	public InvoiceBuilder dueDate(Day dueDate) {
		daysToPay = dueDate.daysBetween(issueDay);
		return this;
	}
}

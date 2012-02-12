package com.aldercape.internal.economics.model;

import java.util.List;

public class Invoice {

	private static final int VAT = 21;
	private Client client;
	private Day issueDate;
	private List<InvoiceEntry> entries;
	private int daysToPay;
	private Client company;

	public Invoice(Client company, Client client, Day issueDate, List<InvoiceEntry> entries, int daysToPay) throws EntryNotForClientException {
		this.company = company;
		this.client = client;
		this.issueDate = issueDate;
		this.entries = entries;
		this.daysToPay = daysToPay;
		assertEntriesForClient();
	}

	private void assertEntriesForClient() {
		for (Entry<Day> entry : entries) {
			Client client2 = client;
			assertIsForClient(entry, client2);
		}
	}

	public Client client() {
		return client;
	}

	public Day issueDate() {
		return issueDate;
	}

	public Euro totalAmount() {
		Euro result = new Euro(0);
		for (Entry<Day> entry : entries) {
			result = result.plus(entry.amount());
		}
		return result;
	}

	public Euro vat() {
		return totalAmount().percentage(VAT);
	}

	public Euro toPay() {
		return totalAmount().plus(vat());
	}

	public Day dueDate() {
		return issueDate().daysAfter(daysToPay);
	}

	public Client company() {
		return company;
	}

	public boolean isOverdue(Day day) {
		return day.after(dueDate());
	}

	public List<InvoiceEntry> entries() {
		return entries;
	}

	public static void assertIsForClient(Entry<Day> entry, Client client) {
		if (!entry.client().name().equals(client.name())) {
			throw new EntryNotForClientException("Invoice is for " + client.name() + " but got " + entry.client().name());
		}
	}

	public static boolean isValid(Entry<Day> entry, Client client) {
		return entry.client().equals(client);
	}

}

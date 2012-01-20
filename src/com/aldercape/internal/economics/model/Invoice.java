package com.aldercape.internal.economics.model;

import java.util.ArrayList;
import java.util.List;

public class Invoice {

	private static final int VAT = 21;
	private Client client;
	private Day issueDate;
	private List<Entry<Day>> entries;

	public Invoice(Client client, Day issueDate) {
		this(client, issueDate, new ArrayList<Entry<Day>>());
	}

	public Invoice(Client client, Day issueDate, List<Entry<Day>> entries) {
		this.client = client;
		this.issueDate = issueDate;
		this.entries = entries;
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

}

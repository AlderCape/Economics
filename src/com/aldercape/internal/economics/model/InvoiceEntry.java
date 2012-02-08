package com.aldercape.internal.economics.model;

import java.util.Set;

public interface InvoiceEntry extends Entry<Day> {

	public Set<TimeEntry> getAllEntries();

	public String title();

	public String description();

}

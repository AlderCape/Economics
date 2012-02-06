package com.aldercape.internal.economics.model;

import java.util.Set;

public interface InvoiceEntry extends Entry<Day> {

	Set<TimeEntry> getAllEntries();

}

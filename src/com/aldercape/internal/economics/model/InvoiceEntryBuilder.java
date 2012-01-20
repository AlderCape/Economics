package com.aldercape.internal.economics.model;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class InvoiceEntryBuilder {

	private Set<TimeEntry> entries;
	InvoiceEntryGroupingRule rule;

	public InvoiceEntryBuilder(Set<TimeEntry> entries) {
		rule = new InvoiceEntryGroupingRule();
		this.entries = new TreeSet<TimeEntry>(rule.getComparator());
		this.entries.addAll(entries);
	}

	public Set<? extends InvoiceEntry> createInvoiceEntry() {
		ComposedInvoiceEntry currentInvoiceEntry = null;
		LinkedHashSet<ComposedInvoiceEntry> result = new LinkedHashSet<ComposedInvoiceEntry>();
		for (TimeEntry entry : entries) {
			if (currentInvoiceEntry == null || !currentInvoiceEntry.belongsTo(entry)) {
				currentInvoiceEntry = new ComposedInvoiceEntry(rule, entry);
				result.add(currentInvoiceEntry);
			} else {
				currentInvoiceEntry.addTimeEntry(entry);
			}
		}
		return result;
	}
}

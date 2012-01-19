package com.aldercape.internal.economics.model;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;


public class InvoiceEntryBuilder {

	private Set<TimeEntry> entries;
	private InvoiceEntryGroupingRule rule;

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
				currentInvoiceEntry = new ComposedInvoiceEntry(entry);
				result.add(currentInvoiceEntry);
			} else {
				currentInvoiceEntry.addTimeEntry(entry);
			}
		}
		return result;
	}

	class ComposedInvoiceEntry implements InvoiceEntry {

		private Set<TimeEntry> entries = new LinkedHashSet<TimeEntry>();

		public ComposedInvoiceEntry(TimeEntry entry) {
			addTimeEntry(entry);
		}

		public void addTimeEntry(TimeEntry entry) {
			this.entries.add(entry);
		}

		@Override
		public Unit units() {
			Unit result = Unit.days(0);
			for (TimeEntry entry : entries) {
				result = result.plus(entry.units());
			}
			return result;
		}

		@Override
		public Euro rate() {
			return entries.iterator().next().rate();
		}

		@Override
		public Collaborator collaborator() {
			return entries.iterator().next().collaborator();
		}

		@Override
		public Client client() {
			return entries.iterator().next().client();
		}

		@Override
		public Day getTimePoint() {
			Day result = Day.LAST_DAY;
			for (TimeEntry entry : entries) {
				result = first(result, entry.getTimePoint());
			}

			return result;
		}

		private Day first(Day a, Day b) {
			if (a.compareTo(b) > 0) {
				return b;
			}
			return a;
		}

		@Override
		public Euro amount() {
			return rate().times(units().days());
		}

		@Override
		public Euro vat() {
			return amount().percentage(21);
		}

		boolean belongsTo(TimeEntry entry) {
			return rule.getCriteria(this).matches(entry);
		}
	}
}

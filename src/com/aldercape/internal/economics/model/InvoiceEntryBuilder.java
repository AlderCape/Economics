package com.aldercape.internal.economics.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class InvoiceEntryBuilder {

	private Set<TimeEntry> entries;

	public InvoiceEntryBuilder(Set<TimeEntry> entries) {
		this.entries = new TreeSet<TimeEntry>(new Comparator<TimeEntry>() {
			@Override
			public int compare(TimeEntry o1, TimeEntry o2) {
				int result;
				result = o1.colaborator().name().compareTo(o2.colaborator().name());
				if (result != 0) {
					return result;
				}
				result = o1.client().name().compareTo(o2.client().name());
				if (result != 0) {
					return result;
				}
				result = o1.rate().compareTo(o2.rate());
				if (result != 0) {
					return result;
				}
				result = o1.getTimePoint().compareTo(o2.getTimePoint());
				if (result != 0) {
					return result;
				}
				if (o1.hashCode() == o2.hashCode()) {
					return 0;
				}
				if (o1.hashCode() < o2.hashCode()) {
					return 1;
				}
				return -1;

			}
		});
		this.entries.addAll(entries);
	}

	public Set<? extends InvoiceEntry> createInvoiceEntry() {
		if (entries.isEmpty()) {
			return Collections.singleton(new InvoiceEntry(Unit.createFrom(0), new Euro(0), new Colaborator(""), new Client(""), Day.LAST_DAY));
		}
		ComposedInvoiceEntry currentInvoiceEntry = null;
		LinkedHashSet<ComposedInvoiceEntry> result = new LinkedHashSet<ComposedInvoiceEntry>();
		for (TimeEntry entry : entries) {
			if (currentInvoiceEntry == null || !belongsTo(currentInvoiceEntry, entry)) {
				currentInvoiceEntry = new ComposedInvoiceEntry();
				result.add(currentInvoiceEntry);
			}
			currentInvoiceEntry.addTimeEntry(entry);
		}
		return result;
	}

	private boolean belongsTo(ComposedInvoiceEntry currentInvoiceEntry, TimeEntry entry) {
		return currentInvoiceEntry.getTimePoint().sameMonth(entry.getTimePoint()) && currentInvoiceEntry.colaborator().name().equals(entry.colaborator().name()) && currentInvoiceEntry.client().name().equals(entry.client().name()) && currentInvoiceEntry.rate().equals(entry.rate());
	}

	private class ComposedInvoiceEntry extends InvoiceEntry {

		private Set<TimeEntry> entries = new LinkedHashSet<TimeEntry>();

		public ComposedInvoiceEntry() {
			super(Unit.days(0), new Euro(0), new Colaborator(""), new Client(""), Day.LAST_DAY);
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
		public Colaborator colaborator() {
			return entries.iterator().next().colaborator();
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

	}
}

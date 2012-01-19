package com.aldercape.internal.economics.model;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import com.aldercape.internal.economics.criteria.AbstractEntryCriteria;
import com.aldercape.internal.economics.criteria.ClientCriteria;
import com.aldercape.internal.economics.criteria.CollaboratorCriteria;
import com.aldercape.internal.economics.criteria.MonthCriteria;
import com.aldercape.internal.economics.criteria.RateCriteria;

public class InvoiceEntryBuilder {

	private Set<TimeEntry> entries;

	public InvoiceEntryBuilder(Set<TimeEntry> entries) {
		this.entries = new TreeSet<TimeEntry>(new Comparator<TimeEntry>() {
			@Override
			public int compare(TimeEntry o1, TimeEntry o2) {
				int result;
				result = o1.collaborator().compareTo(o2.collaborator());
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
				result = o1.client().name().compareTo(o2.client().name());
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
			AbstractEntryCriteria<Day> criteria = new MonthCriteria<Day>(getTimePoint());
			criteria = criteria.and(new CollaboratorCriteria<Day>(collaborator()));
			criteria = criteria.and(new ClientCriteria<Day>(client()));
			criteria = criteria.and(new RateCriteria<Day>(rate()));
			return criteria.matches(entry);

		}
	}
}

package com.aldercape.internal.economics.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class TimeEntryReport {

	private List<TimeEntry> entries;

	public TimeEntryReport(List<TimeEntry> entries) {
		this.entries = entries;
	}

	public int getEntriesCount() {
		return entries.size();
	}

	public List<InvoiceEntry> getInvoiceEntry() {
		Map<GroupingKey, InvoiceEntry> groups = new HashMap<GroupingKey, InvoiceEntry>();
		for (TimeEntry time : entries) {
			GroupingKey key = new GroupingKey(time);
			groups.put(key, getCurrent(groups, time, key).addTime(time.units()));
		}
		return getValuesOrderedByKey(groups);
	}

	private List<InvoiceEntry> getValuesOrderedByKey(Map<GroupingKey, InvoiceEntry> groups) {
		ArrayList<InvoiceEntry> result = new ArrayList<InvoiceEntry>();
		for (GroupingKey month : new TreeSet<GroupingKey>(groups.keySet())) {
			result.add(groups.get(month));
		}
		return result;
	}

	private InvoiceEntry getCurrent(Map<GroupingKey, InvoiceEntry> groups, TimeEntry time, GroupingKey key) {
		if (!groups.containsKey(key)) {
			return new InvoiceEntry(Unit.days(0), time.rate(), time.colaborator(), time.client(), time.month(), time.month());
		} else {
			return groups.get(key);
		}
	}

	private class GroupingKey implements Comparable<GroupingKey> {

		private Month month;
		private Colaborator colaborator;
		private Client client;
		private Euro rate;

		public GroupingKey(TimeEntry time) {
			this.month = time.month();
			this.colaborator = time.colaborator();
			this.client = time.client();
			this.rate = time.rate();
		}

		@Override
		public boolean equals(Object arg0) {
			GroupingKey other = (GroupingKey) arg0;
			return month.equals(other.month) && colaborator.name().equals(other.colaborator.name()) && client.name().equals(other.client.name()) && rate.equals(other.rate);
		}

		@Override
		public int hashCode() {
			return month.hashCode() ^ colaborator.name().hashCode() ^ client.name().hashCode() ^ rate.hashCode();
		}

		@Override
		public int compareTo(GroupingKey arg0) {
			int result = month.compareTo(arg0.month);
			if (result != 0) {
				return result;
			}
			result = colaborator.name().compareToIgnoreCase(arg0.colaborator.name());
			if (result != 0) {
				return result;
			}
			result = client.name().compareToIgnoreCase(arg0.client.name());
			if (result != 0) {
				return result;
			}
			return rate.compareTo(arg0.rate);
		}
	}
}

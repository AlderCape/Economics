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

		public GroupingKey(TimeEntry time) {
			this.month = time.month();
			this.colaborator = time.colaborator();
		}

		@Override
		public boolean equals(Object arg0) {
			GroupingKey other = (GroupingKey) arg0;
			return month.equals(other.month) && colaborator.name().equals(other.colaborator.name());
		}

		@Override
		public int hashCode() {
			return month.hashCode() ^ colaborator.name().hashCode();
		}

		@Override
		public int compareTo(GroupingKey arg0) {
			return month.compareTo(arg0.month);
		}

	}
}

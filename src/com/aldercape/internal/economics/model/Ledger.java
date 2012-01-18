package com.aldercape.internal.economics.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Ledger {

	private List<Entry<Day>> entries = new ArrayList<Entry<Day>>();

	public int numberOfEntries() {
		return entries.size();
	}

	public void addEntry(Entry<Day> entry) {
		entries.add(entry);
	}

	public Euro totalAmount() {
		Euro result = new Euro(0);
		for (Entry<Day> entry : entries) {
			result = result.plus(entry.amount());
		}
		return result;
	}

	public Set<Month> bookkeepingMonths() {
		Set<Month> result = new HashSet<Month>();
		for (Entry<Day> entry : entries) {
			result.add(entry.getTimePoint().month());
		}
		return result;
	}

	public List<Ledger> groupByBookkeepingMonth() {
		Map<Month, Ledger> resultMap = new HashMap<Month, Ledger>();
		for (Entry<Day> entry : entries) {
			if (resultMap.get(entry.getTimePoint().month()) == null) {
				resultMap.put(entry.getTimePoint().month(), new Ledger());
			}
			resultMap.get(entry.getTimePoint().month()).addEntry(entry);
		}
		return createResult(resultMap);
	}

	private List<Ledger> createResult(Map<Month, Ledger> resultMap) {
		List<Ledger> result = new ArrayList<Ledger>();
		TreeSet<Month> sortedKeys = new TreeSet<Month>(resultMap.keySet());
		for (Month month : sortedKeys) {
			result.add(resultMap.get(month));
		}
		return result;
	}

	public Entry<Day> entry(int index) {
		return entries.get(index);
	}

	public void removeEntries(Set<? extends Entry<Day>> toRemove) {
		entries.removeAll(toRemove);
	}

	public void addEntries(Set<? extends Entry<Day>> toAdd) {
		entries.addAll(toAdd);
	}

	public Ledger filter(EntryCriteria<Day> criteria) {
		Ledger result = new Ledger();
		for (Entry<Day> entry : entries) {
			if (criteria.matches(entry)) {
				result.addEntry(entry);
			}
		}
		return result;
	}

}

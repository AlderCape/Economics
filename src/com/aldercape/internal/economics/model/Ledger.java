package com.aldercape.internal.economics.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Ledger {

	private List<InvoiceEntry> entries = new ArrayList<InvoiceEntry>();

	public int numberOfEntries() {
		return entries.size();
	}

	public void addEntry(InvoiceEntry entry) {
		entries.add(entry);
	}

	public Euro totalAmount() {
		Euro result = new Euro(0);
		for (InvoiceEntry entry : entries) {
			result = result.plus(entry.amount());
		}
		return result;
	}

	public Set<Month> bookkeepingMonths() {
		Set<Month> result = new HashSet<Month>();
		for (InvoiceEntry entry : entries) {
			result.add(entry.bookkeepingMonth());
		}
		return result;
	}

	public Set<Month> cashflowMonths() {
		Set<Month> result = new HashSet<Month>();
		for (InvoiceEntry entry : entries) {
			result.add(entry.cashflowMonth());
		}
		return result;
	}

	public List<Ledger> groupByBookkeepingMonth() {
		Map<Month, Ledger> resultMap = new HashMap<Month, Ledger>();
		for (InvoiceEntry entry : entries) {
			if (resultMap.get(entry.bookkeepingMonth()) == null) {
				resultMap.put(entry.bookkeepingMonth(), new Ledger());
			}
			resultMap.get(entry.bookkeepingMonth()).addEntry(entry);
		}
		return createResult(resultMap);
	}

	public List<Ledger> groupByCashflowMonth() {
		Map<Month, Ledger> resultMap = new HashMap<Month, Ledger>();
		for (InvoiceEntry entry : entries) {
			if (resultMap.get(entry.cashflowMonth()) == null) {
				resultMap.put(entry.cashflowMonth(), new Ledger());
			}
			resultMap.get(entry.cashflowMonth()).addEntry(entry);
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

	public InvoiceEntry entry(int index) {
		return entries.get(index);
	}

}

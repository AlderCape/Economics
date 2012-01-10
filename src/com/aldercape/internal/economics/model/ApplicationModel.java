package com.aldercape.internal.economics.model;

import java.util.ArrayList;
import java.util.List;

public class ApplicationModel {

	private List<LedgerListener> ledgerListeners = new ArrayList<LedgerListener>();
	private Ledger ledger;

	public ApplicationModel(Ledger ledger) {
		this.ledger = ledger;
	}

	public void addEntry(Entry<Day> entry) {
		ledger.addEntry(entry);
		for (LedgerListener listener : ledgerListeners) {
			listener.ledgerUpdated();
		}
	}

	public void addLedgerListner(LedgerListener ledgerListener) {
		this.ledgerListeners.add(ledgerListener);

	}

}
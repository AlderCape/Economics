package com.aldercape.internal.economics;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.model.LedgerListener;

public class ApplicationModel {

	private List<LedgerListener> ledgerListeners = new ArrayList<LedgerListener>();
	private Ledger ledger;

	public ApplicationModel(Ledger ledger) {
		this.ledger = ledger;
	}

	public void addEntry(Entry<Day> entry) {
		ledger.addEntry(entry);
		notifyListeners();
	}

	private void notifyListeners() {
		for (LedgerListener listener : ledgerListeners) {
			listener.ledgerUpdated();
		}
	}

	public void addLedgerListner(LedgerListener ledgerListener) {
		this.ledgerListeners.add(ledgerListener);

	}

	public Ledger ledger() {
		return ledger;
	}

	public void removeEntries(Set<? extends Entry<Day>> entries) {
		ledger.removeEntries(entries);
		notifyListeners();
	}

	public void addEntries(Set<? extends Entry<Day>> toAdd) {
		ledger.addEntries(toAdd);
		notifyListeners();
	}

}

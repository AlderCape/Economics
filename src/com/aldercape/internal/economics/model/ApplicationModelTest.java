package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ApplicationModelTest {

	private boolean ledgerUpdated;
	private boolean addEntryCalled;

	@Test
	public void addEntryShouldCallLedgerAndNotifyListeners() {
		Ledger ledger = new Ledger() {
			@Override
			public void addEntry(InvoiceEntry entry) {
				addEntryCalled = true;
				super.addEntry(entry);
			}
		};
		ApplicationModel model = new ApplicationModel(ledger);
		model.addLedgerListner(new LedgerListener() {
			@Override
			public void ledgerUpdated() {
				ledgerUpdated = true;
			}
		});
		assertFalse(addEntryCalled);
		assertFalse(ledgerUpdated);
		model.addEntry(new InvoiceEntry(Unit.days(1), new Euro(10), new Colaborator("Col"), new Client("client"), Month.january(2011), Month.february(2011)));
		assertTrue(addEntryCalled);
		assertTrue(ledgerUpdated);
	}
}

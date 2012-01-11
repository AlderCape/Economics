package com.aldercape.internal.economics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Colaborator;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.model.LedgerListener;
import com.aldercape.internal.economics.model.Unit;

public class ApplicationModelTest {

	private boolean ledgerUpdated;
	private boolean addEntryCalled;
	private Ledger ledger;
	private ApplicationModel model;

	@Before
	public void setUp() {
		ledger = new Ledger() {
			@Override
			public void addEntry(Entry<Day> entry) {
				addEntryCalled = true;
				super.addEntry(entry);
			}
		};
		model = new ApplicationModel(ledger);
		model.addLedgerListner(new LedgerListener() {
			@Override
			public void ledgerUpdated() {
				ledgerUpdated = true;
			}
		});
	}

	@Test
	public void addInvoiceEntryShouldCallLedgerAndNotifyListeners() {
		assertFalse(addEntryCalled);
		assertFalse(ledgerUpdated);
		model.addEntry(new InvoiceEntry(Unit.days(1), new Euro(10), new Colaborator("Col"), new Client("client"), Day.january(1, 2011)));
		assertTrue(addEntryCalled);
		assertTrue(ledgerUpdated);
	}
}

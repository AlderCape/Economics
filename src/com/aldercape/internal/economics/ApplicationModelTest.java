package com.aldercape.internal.economics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.model.LedgerListener;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class ApplicationModelTest {

	private boolean ledgerUpdated;
	private boolean addEntryCalled;
	private Ledger ledger;
	private ApplicationModel model;
	private InvoiceEntry entry;

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
		__TestObjectMother objectMother = new __TestObjectMother();
		entry = new InvoiceEntry(Unit.days(1), new Euro(10), objectMother.me(), objectMother.myCompany(), Day.january(1, 2011));
	}

	@Test
	public void addInvoiceEntryShouldCallLedgerAndNotifyListeners() {
		assertFalse(addEntryCalled);
		assertFalse(ledgerUpdated);
		model.addEntry(entry);
		assertTrue(addEntryCalled);
		assertTrue(ledgerUpdated);
	}
}

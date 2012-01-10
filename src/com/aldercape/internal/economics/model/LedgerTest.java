package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class LedgerTest {

	@Test
	public void noEntries() {
		Ledger ledger = new Ledger();
		assertEquals("No entries when created", 0, ledger.numberOfEntries());
		assertEquals("Amount", new Euro(0), ledger.totalAmount());
		assertEquals("Bookkeeping Months", Collections.emptySet(), ledger.bookkeepingMonths());
	}

	@Test
	public void oneEntry() {
		Ledger ledger = new Ledger();
		ledger.addEntry(new InvoiceEntry(Unit.days(2), new Euro(200), new Colaborator("Me"), new Client("Client"), Day.january(1, 2011)));
		assertEquals("One entry after addition", 1, ledger.numberOfEntries());
		assertEquals("Amount with one entry", new Euro(400), ledger.totalAmount());
		assertEquals("BookkeepingMonths", Collections.singleton(Month.january(2011)), ledger.bookkeepingMonths());
	}

	@Test
	public void twoEntries() {
		Set<Month> expectedBookkeepingMonths = new HashSet<Month>();
		expectedBookkeepingMonths.add(Month.january(2011));
		expectedBookkeepingMonths.add(Month.february(2011));

		Set<Month> expectedCashflowMonths = new HashSet<Month>();
		expectedCashflowMonths.add(Month.february(2011));
		expectedCashflowMonths.add(Month.mars(2011));

		Ledger ledger = createLEdgerWithTwoEntries();
		assertEquals("Amount with one entry", new Euro(850), ledger.totalAmount());
		assertEquals("Bookkeeping Months", expectedBookkeepingMonths, ledger.bookkeepingMonths());
	}

	private Ledger createLEdgerWithTwoEntries() {
		Ledger ledger = new Ledger();
		ledger.addEntry(new InvoiceEntry(Unit.days(2), new Euro(200), new Colaborator("Me"), new Client("Client"), Day.january(1, 2011)));
		ledger.addEntry(new InvoiceEntry(Unit.days(3), new Euro(150), new Colaborator("Me"), new Client("Client"), Day.february(1, 2011)));
		return ledger;
	}

	@Test
	public void groupByBookkeepingMonth() {
		Ledger ledger = createLedgerWithThreeEntries();

		List<Ledger> result = new ArrayList<Ledger>(ledger.groupByBookkeepingMonth());
		assertEquals(2, result.size());
		assertEquals(Collections.singleton(Month.january(2011)), result.get(0).bookkeepingMonths());
		assertEquals(2, result.get(0).numberOfEntries());

		assertEquals(Collections.singleton(Month.february(2011)), result.get(1).bookkeepingMonths());
		assertEquals(1, result.get(1).numberOfEntries());
	}

	private Ledger createLedgerWithThreeEntries() {
		Ledger ledger = createLEdgerWithTwoEntries();
		ledger.addEntry(new InvoiceEntry(Unit.days(2), new Euro(200), new Colaborator("Someone"), new Client("Client"), Day.january(1, 2011)));
		return ledger;
	}

	@Test
	public void entriesShouldBeRetreivable() {
		Ledger ledger = new Ledger();
		InvoiceEntry entry = new InvoiceEntry(Unit.days(2), new Euro(200), new Colaborator("Me"), new Client("Client"), Day.january(1, 2011));
		ledger.addEntry(entry);
		assertSame(entry, ledger.entry(0));
	}
}

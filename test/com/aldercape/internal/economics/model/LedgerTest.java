package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.criteria.ClientCriteria;
import com.aldercape.internal.economics.criteria.CollaboratorCriteria;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class LedgerTest {

	private Collaborator me;
	private Client myCompany;

	@Before
	public void setUp() {
		__TestObjectMother objectMother = new __TestObjectMother();
		me = objectMother.me();
		myCompany = objectMother.myCompany();
	}

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
		ledger.addEntry(new SimpleInvoiceEntry(Unit.days(2), Rate.daily(new Euro(200)), me, myCompany, Day.january(1, 2011)));
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
		ledger.addEntry(new SimpleInvoiceEntry(Unit.days(2), Rate.daily(new Euro(200)), me, myCompany, Day.january(1, 2011)));
		ledger.addEntry(new SimpleInvoiceEntry(Unit.days(3), Rate.daily(new Euro(150)), me, myCompany, Day.february(1, 2011)));
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
		ledger.addEntry(new SimpleInvoiceEntry(Unit.days(2), Rate.daily(new Euro(200)), me, myCompany, Day.january(1, 2011)));
		return ledger;
	}

	@Test
	public void entriesShouldBeRetreivable() {
		Ledger ledger = new Ledger();
		SimpleInvoiceEntry entry = new SimpleInvoiceEntry(Unit.days(2), Rate.daily(new Euro(200)), me, myCompany, Day.january(1, 2011));
		ledger.addEntry(entry);
		assertSame(entry, ledger.entry(0));
	}

	@Test
	public void filterByClient() {
		Ledger ledger = createLedgerWithThreeEntries();
		__TestObjectMother objectMother = new __TestObjectMother();
		SimpleInvoiceEntry entryToKeep = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(300)), objectMother.me(), objectMother.otherCompany(), Day.january(1, 2012));
		ledger.addEntry(entryToKeep);
		ledger = ledger.filter(new ClientCriteria<Day>(objectMother.otherCompany()));
		assertEquals(1, ledger.numberOfEntries());
		CustomModelAsserts.assertInvoiceEntryEquals(entryToKeep, ledger.entry(0));
	}

	@Test
	public void filterByCollaborator() {
		Ledger ledger = createLedgerWithThreeEntries();
		__TestObjectMother objectMother = new __TestObjectMother();
		SimpleInvoiceEntry entryToKeep = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(300)), objectMother.other(), objectMother.otherCompany(), Day.january(1, 2012));
		ledger.addEntry(entryToKeep);
		ledger = ledger.filter(new CollaboratorCriteria<Day>(objectMother.other()));
		assertEquals(1, ledger.numberOfEntries());
		CustomModelAsserts.assertInvoiceEntryEquals(entryToKeep, ledger.entry(0));
	}

}

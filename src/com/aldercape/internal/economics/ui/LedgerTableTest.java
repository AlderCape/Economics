package com.aldercape.internal.economics.ui;

import static com.aldercape.internal.economics.model.CustomModelAsserts.assertInvoiceEntryEquals;
import static com.aldercape.internal.economics.model.CustomModelAsserts.assertTimeEntryEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Set;

import javax.swing.ListSelectionModel;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.model.SimpleInvoiceEntry;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.Unit;

public class LedgerTableTest {

	private Collaborator me;
	private Client myCompany;

	@Before
	public void setUp() {
		__TestObjectMother objectMother = new __TestObjectMother();
		me = objectMother.me();
		myCompany = objectMother.myCompany();
	}

	@Test
	public void layout() {
		LedgerTable table = new LedgerTable(new ApplicationModel(new Ledger()));
		assertSame(LedgerTableModel.class, table.getModel().getClass());
		assertEquals(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, table.getSelectionModel().getSelectionMode());
	}

	@Test
	public void createTimeEntriesFromSelectionSingleSelection() {
		Ledger ledger = new Ledger();
		ledger.addEntry(new TimeEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012)));
		LedgerTable table = new LedgerTable(new ApplicationModel(ledger));
		table.getSelectionModel().setSelectionInterval(0, 0);
		Set<TimeEntry> entries = table.createTimeEntriesFromSelection();
		TimeEntry expected = new TimeEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012));
		assertTimeEntryEquals(expected, entries.iterator().next());
	}

	@Test
	public void createTimeEntriesFromSelectionMultipleSelectionsSameDay() {
		Ledger ledger = new Ledger();
		ledger.addEntry(new TimeEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012)));
		ledger.addEntry(new TimeEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012)));
		ledger.addEntry(new TimeEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012)));
		ledger.addEntry(new TimeEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012)));
		LedgerTable table = new LedgerTable(new ApplicationModel(ledger));
		table.getSelectionModel().setSelectionInterval(1, 2);
		Set<TimeEntry> entries = table.createTimeEntriesFromSelection();
		assertEquals(2, entries.size());
		TimeEntry expected = new TimeEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012));
		assertTimeEntryEquals(expected, entries.iterator().next());
	}

	@Test
	public void createInvoiceEntriesFromSelectionCallsBuilder() {
		Ledger ledger = new Ledger();
		ledger.addEntry(new TimeEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012)));
		ledger.addEntry(new TimeEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012)));
		ledger.addEntry(new TimeEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012)));
		ledger.addEntry(new TimeEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012)));
		LedgerTable table = new LedgerTable(new ApplicationModel(ledger));
		table.getSelectionModel().setSelectionInterval(1, 2);
		Set<? extends InvoiceEntry> entries = table.createInvoiceEntriesFromSelection();
		assertEquals(1, entries.size());
		assertInvoiceEntryEquals(new SimpleInvoiceEntry(Unit.days(2), new Euro(10), me, myCompany, Day.january(4, 2012)), entries.iterator().next());
	}

	@Test
	public void createInvoiceEntriesFromSelectionIgnoresSelectedInvoiceItems() {
		Ledger ledger = new Ledger();
		ledger.addEntry(new SimpleInvoiceEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012)));
		ledger.addEntry(new TimeEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012)));
		LedgerTable table = new LedgerTable(new ApplicationModel(ledger));
		table.getSelectionModel().setSelectionInterval(0, 1);
		Set<? extends InvoiceEntry> entries = table.createInvoiceEntriesFromSelection();
		assertEquals(1, entries.size());
		assertInvoiceEntryEquals(new SimpleInvoiceEntry(Unit.days(1), new Euro(10), me, myCompany, Day.january(4, 2012)), entries.iterator().next());
	}
}

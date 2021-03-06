package com.aldercape.internal.economics.model;

import static com.aldercape.internal.economics.model.CustomModelAsserts.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ui.__TestObjectMother;

public class InvoiceEntryBuilderTest {

	private Collaborator other;
	private Collaborator me;
	private Client myCompany;
	private Client otherCompany;

	@Before
	public void setUp() {
		__TestObjectMother objectMother = new __TestObjectMother();
		other = objectMother.other();
		me = objectMother.me();
		myCompany = objectMother.myCompany();
		otherCompany = objectMother.otherCompany();
	}

	@Test
	public void noEntries() {
		Set<TimeEntry> entries = Collections.emptySet();
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		Set<InvoiceEntry> invoiceEntry = builder.createInvoiceEntry();
		assertTrue(invoiceEntry.isEmpty());
	}

	@Test
	public void oneEntry() {
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(Collections.singleton(createTimeEntry(me, myCompany, Day.january(3, 2012))));
		InvoiceEntry invoiceEntry = builder.createInvoiceEntry().iterator().next();
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(50)), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected, invoiceEntry);
	}

	@Test
	public void twoEntriesLowestDayAsTimePoint() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(createTimeEntry(me, myCompany, Day.january(3, 2012)));
		entries.add(createTimeEntry(me, myCompany, Day.january(4, 2012)));
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		InvoiceEntry invoiceEntry = builder.createInvoiceEntry().iterator().next();
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(2), Rate.daily(new Euro(50)), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected, invoiceEntry);
	}

	@Test
	public void twoEntriesDifferentMonths() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(createTimeEntry(me, myCompany, Day.january(3, 2012)));
		entries.add(createTimeEntry(me, myCompany, Day.february(3, 2012)));
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		Set<InvoiceEntry> invoiceEntry = builder.createInvoiceEntry();
		assertEquals(2, invoiceEntry.size());
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(50)), me, myCompany, Day.january(3, 2012));
		Iterator<InvoiceEntry> iterator = invoiceEntry.iterator();
		assertInvoiceEntryEquals(expected, iterator.next());
		InvoiceEntry expected2 = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(50)), me, myCompany, Day.february(3, 2012));
		assertInvoiceEntryEquals(expected2, iterator.next());
	}

	@Test
	public void twoEntriesDifferentCollaborators() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(createTimeEntry(me, myCompany, Day.january(3, 2012)));
		entries.add(createTimeEntry(other, myCompany, Day.january(3, 2012)));
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		Set<InvoiceEntry> invoiceEntry = builder.createInvoiceEntry();
		Iterator<InvoiceEntry> iterator = invoiceEntry.iterator();
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(50)), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected, iterator.next());
		InvoiceEntry expected2 = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(50)), other, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected2, iterator.next());
	}

	@Test
	public void twoEntriesDifferentClients() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(createTimeEntry(me, myCompany, Day.january(3, 2012)));
		entries.add(createTimeEntry(me, otherCompany, Day.january(3, 2012)));
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		Set<InvoiceEntry> invoiceEntry = builder.createInvoiceEntry();
		Iterator<InvoiceEntry> iterator = invoiceEntry.iterator();
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(50)), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected, iterator.next());
		InvoiceEntry expected2 = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(50)), me, otherCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected2, iterator.next());
	}

	@Test
	public void twoEntriesDifferentRates() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(createTimeEntry(me, myCompany, Day.january(3, 2012)));
		entries.add(createTimeEntry(new Euro(150), me, myCompany, Day.january(3, 2012)));
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		Set<InvoiceEntry> invoiceEntry = builder.createInvoiceEntry();
		Iterator<InvoiceEntry> iterator = invoiceEntry.iterator();
		assertEquals(2, invoiceEntry.size());
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(50)), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected, iterator.next());
		InvoiceEntry expected2 = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(150)), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected2, iterator.next());
	}

	@Test
	public void twoEqualEntries() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(createTimeEntry(me, myCompany, Day.january(3, 2012)));
		entries.add(createTimeEntry(me, myCompany, Day.january(3, 2012)));
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		InvoiceEntry invoiceEntry = builder.createInvoiceEntry().iterator().next();
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(2), Rate.daily(new Euro(50)), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected, invoiceEntry);
	}

	@Test
	public void manyEntriesSortedByCollaborator() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(createTimeEntry(new Euro(200), me, myCompany, Day.january(1, 2012)));
		entries.add(createTimeEntry(new Euro(200), me, myCompany, Day.january(2, 2012)));
		entries.add(createTimeEntry(new Euro(200), me, myCompany, Day.january(3, 2012)));
		entries.add(createTimeEntry(new Euro(200), other, myCompany, Day.january(1, 2012)));
		entries.add(createTimeEntry(new Euro(200), other, myCompany, Day.january(2, 2012)));
		entries.add(createTimeEntry(new Euro(200), other, myCompany, Day.january(3, 2012)));

		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		Iterator<InvoiceEntry> result = builder.createInvoiceEntry().iterator();
		InvoiceEntry invoiceEntry = result.next();
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(3), Rate.daily(new Euro(200)), me, myCompany, Day.january(1, 2012));
		assertInvoiceEntryEquals(expected, invoiceEntry);

		invoiceEntry = result.next();
		expected = new SimpleInvoiceEntry(Unit.days(3), Rate.daily(new Euro(200)), other, myCompany, Day.january(1, 2012));
		assertInvoiceEntryEquals(expected, invoiceEntry);

	}

	private TimeEntry createTimeEntry(Euro amount, Collaborator collaborator, Client client, Day day) {
		return new TimeEntry(Unit.days(1), Rate.daily(amount), collaborator, client, day);
	}

	public TimeEntry createTimeEntry(Collaborator collaborator, Client client, Day day) {
		return createTimeEntry(new Euro(50), collaborator, client, day);
	}

}

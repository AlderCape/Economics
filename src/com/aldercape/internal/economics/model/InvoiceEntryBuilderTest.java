package com.aldercape.internal.economics.model;

import static com.aldercape.internal.economics.model.CustomModelAsserts.assertInvoiceEntryEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
		Set<? extends InvoiceEntry> invoiceEntry = builder.createInvoiceEntry();
		assertTrue(invoiceEntry.isEmpty());
	}

	@Test
	public void oneEntry() {
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(Collections.singleton(new TimeEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(3, 2012))));
		InvoiceEntry invoiceEntry = builder.createInvoiceEntry().iterator().next();
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected, invoiceEntry);
	}

	@Test
	public void twoEntriesLowestDayAsTimePoint() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(new TimeEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(3, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(4, 2012)));
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		InvoiceEntry invoiceEntry = builder.createInvoiceEntry().iterator().next();
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(2), new Euro(50), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected, invoiceEntry);
	}

	@Test
	public void twoEntriesDifferentMonths() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(new TimeEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(3, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(50), me, myCompany, Day.february(3, 2012)));
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		Set<? extends InvoiceEntry> invoiceEntry = builder.createInvoiceEntry();
		assertEquals(2, invoiceEntry.size());
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(3, 2012));
		Iterator<? extends InvoiceEntry> iterator = invoiceEntry.iterator();
		assertInvoiceEntryEquals(expected, iterator.next());
		InvoiceEntry expected2 = new SimpleInvoiceEntry(Unit.days(1), new Euro(50), me, myCompany, Day.february(3, 2012));
		assertInvoiceEntryEquals(expected2, iterator.next());
	}

	@Test
	public void twoEntriesDifferentColaborators() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(new TimeEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(3, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(50), other, myCompany, Day.january(3, 2012)));
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		Set<? extends InvoiceEntry> invoiceEntry = builder.createInvoiceEntry();
		Iterator<? extends InvoiceEntry> iterator = invoiceEntry.iterator();
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected, iterator.next());
		InvoiceEntry expected2 = new SimpleInvoiceEntry(Unit.days(1), new Euro(50), other, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected2, iterator.next());
	}

	@Test
	public void twoEntriesDifferentClients() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(new TimeEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(3, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(50), me, otherCompany, Day.january(3, 2012)));
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		Set<? extends InvoiceEntry> invoiceEntry = builder.createInvoiceEntry();
		Iterator<? extends InvoiceEntry> iterator = invoiceEntry.iterator();
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected, iterator.next());
		InvoiceEntry expected2 = new SimpleInvoiceEntry(Unit.days(1), new Euro(50), me, otherCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected2, iterator.next());
	}

	@Test
	public void twoEntriesDifferentRates() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(new TimeEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(3, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(150), me, myCompany, Day.january(3, 2012)));
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		Set<? extends InvoiceEntry> invoiceEntry = builder.createInvoiceEntry();
		Iterator<? extends InvoiceEntry> iterator = invoiceEntry.iterator();
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected, iterator.next());
		InvoiceEntry expected2 = new SimpleInvoiceEntry(Unit.days(1), new Euro(150), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected2, iterator.next());
	}

	@Test
	public void twoEqualEntries() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(new TimeEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(3, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(50), me, myCompany, Day.january(3, 2012)));
		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		InvoiceEntry invoiceEntry = builder.createInvoiceEntry().iterator().next();
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(2), new Euro(50), me, myCompany, Day.january(3, 2012));
		assertInvoiceEntryEquals(expected, invoiceEntry);
	}

	@Test
	public void manyEntriesSortedByColaborator() {
		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), me, myCompany, Day.january(1, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), me, myCompany, Day.january(2, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), me, myCompany, Day.january(3, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), other, myCompany, Day.january(1, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), other, myCompany, Day.january(2, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), other, myCompany, Day.january(3, 2012)));

		InvoiceEntryBuilder builder = new InvoiceEntryBuilder(entries);
		Iterator<? extends InvoiceEntry> result = builder.createInvoiceEntry().iterator();
		InvoiceEntry invoiceEntry = result.next();
		InvoiceEntry expected = new SimpleInvoiceEntry(Unit.days(3), new Euro(200), me, myCompany, Day.january(1, 2012));
		assertInvoiceEntryEquals(expected, invoiceEntry);

		invoiceEntry = result.next();
		expected = new SimpleInvoiceEntry(Unit.days(3), new Euro(200), other, myCompany, Day.january(1, 2012));
		assertInvoiceEntryEquals(expected, invoiceEntry);

	}

}

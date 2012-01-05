package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TimeEntryReportTest {

	@Test
	public void oneEntry() {
		TimeEntryReport report = new TimeEntryReport(Collections.singletonList(new TimeEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Day.january(10, 2012))));
		assertEquals(1, report.getEntriesCount());
	}

	@Test
	public void createOneInvoiceEntry() {
		TimeEntryReport report = new TimeEntryReport(Collections.singletonList(new TimeEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Day.january(10, 2012))));
		InvoiceEntry expected = new InvoiceEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Month.january(2012), Month.january(2012));
		InvoiceEntry entry = report.getInvoiceEntry().get(0);
		assertInvoiceEntryEquals(entry, expected);
	}

	@Test
	public void createTwoInvoiceEntry() {
		List<TimeEntry> entries = new ArrayList<TimeEntry>();
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Day.january(10, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Day.january(11, 2012)));

		TimeEntryReport report = new TimeEntryReport(entries);
		InvoiceEntry expected = new InvoiceEntry(Unit.days(2), new Euro(200), new Colaborator("Me"), new Client("Client"), Month.january(2012), Month.january(2012));
		InvoiceEntry entry = report.getInvoiceEntry().get(0);
		assertInvoiceEntryEquals(entry, expected);
	}

	@Test
	public void createTwoInvoiceEntriesDifferentMonths() {
		List<TimeEntry> entries = new ArrayList<TimeEntry>();
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Day.january(10, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Day.february(11, 2012)));

		TimeEntryReport report = new TimeEntryReport(entries);
		InvoiceEntry expected1 = new InvoiceEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Month.january(2012), Month.january(2012));
		InvoiceEntry expected2 = new InvoiceEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Month.february(2012), Month.february(2012));
		List<InvoiceEntry> entry = report.getInvoiceEntry();
		assertEquals(2, entry.size());
		assertInvoiceEntryEquals(expected1, entry.get(0));
		assertInvoiceEntryEquals(expected2, entry.get(1));
	}

	@Test
	public void createTwoInvoiceEntriesDifferentColaborators() {
		List<TimeEntry> entries = new ArrayList<TimeEntry>();
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Day.january(10, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), new Colaborator("Other"), new Client("Client"), Day.january(11, 2012)));

		TimeEntryReport report = new TimeEntryReport(entries);
		InvoiceEntry expected1 = new InvoiceEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Month.january(2012), Month.january(2012));
		InvoiceEntry expected2 = new InvoiceEntry(Unit.days(1), new Euro(200), new Colaborator("Other"), new Client("Client"), Month.january(2012), Month.january(2012));
		List<InvoiceEntry> entry = report.getInvoiceEntry();
		assertEquals(2, entry.size());
		assertInvoiceEntryEquals(expected1, entry.get(0));
		assertInvoiceEntryEquals(expected2, entry.get(1));
	}

	@Test
	public void createTwoInvoiceEntriesDifferentClients() {
		List<TimeEntry> entries = new ArrayList<TimeEntry>();
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Day.january(10, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Other Client"), Day.january(11, 2012)));

		TimeEntryReport report = new TimeEntryReport(entries);
		InvoiceEntry expected1 = new InvoiceEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Month.january(2012), Month.january(2012));
		InvoiceEntry expected2 = new InvoiceEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Other Client"), Month.january(2012), Month.january(2012));
		List<InvoiceEntry> entry = report.getInvoiceEntry();
		assertEquals(2, entry.size());
		assertInvoiceEntryEquals(expected1, entry.get(0));
		assertInvoiceEntryEquals(expected2, entry.get(1));
	}

	@Test
	public void createTwoInvoiceEntriesDifferentRate() {
		List<TimeEntry> entries = new ArrayList<TimeEntry>();
		entries.add(new TimeEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Day.january(10, 2012)));
		entries.add(new TimeEntry(Unit.days(1), new Euro(201), new Colaborator("Me"), new Client("Client"), Day.january(11, 2012)));

		TimeEntryReport report = new TimeEntryReport(entries);
		InvoiceEntry expected1 = new InvoiceEntry(Unit.days(1), new Euro(200), new Colaborator("Me"), new Client("Client"), Month.january(2012), Month.january(2012));
		InvoiceEntry expected2 = new InvoiceEntry(Unit.days(1), new Euro(201), new Colaborator("Me"), new Client("Client"), Month.january(2012), Month.january(2012));
		List<InvoiceEntry> entry = report.getInvoiceEntry();
		assertEquals(2, entry.size());
		assertInvoiceEntryEquals(expected1, entry.get(0));
		assertInvoiceEntryEquals(expected2, entry.get(1));
	}

	private void assertInvoiceEntryEquals(InvoiceEntry entry, InvoiceEntry expected) {
		assertEquals("Units", expected.units(), entry.units());
		assertEquals("Rate", expected.rate(), entry.rate());
		assertEquals("Colaborator", expected.colaborator().name(), entry.colaborator().name());
		assertEquals("Client", expected.client().name(), entry.client().name());
		assertEquals("Bookkeeping month", expected.bookkeepingMonth(), entry.bookkeepingMonth());
		assertEquals("Cashflow month", expected.cashflowMonth(), entry.cashflowMonth());
	}
}

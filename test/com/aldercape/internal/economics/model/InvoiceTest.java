package com.aldercape.internal.economics.model;

import static com.aldercape.internal.economics.model.CustomModelAsserts.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ui.__TestObjectMother;

public class InvoiceTest {

	private __TestObjectMother objectMother;
	private Client company;
	private Client client;
	private Collaborator worker;
	private List<InvoiceEntry> entries;

	@Before
	public void setUp() {
		objectMother = new __TestObjectMother();
		company = objectMother.myCompany();
		client = objectMother.otherCompany();
		worker = objectMother.me();
		entries = new ArrayList<>();
	}

	@Test
	public void staticInfo() {
		Invoice invoice = createInvoice();
		assertClientEquals(company, invoice.company());
		assertClientEquals(client, invoice.client());
		assertEquals(Day.january(31, 2012), invoice.issueDate());
		assertEquals(new Euro(0), invoice.totalAmount());
		assertEquals(new Euro(0), invoice.vat());
		assertEquals(new Euro(0), invoice.toPay());
		assertEquals(Day.mars(31, 2012), invoice.dueDate());
	}

	@Test
	public void oneEntryInvoice() {
		entries.add(new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(100)), worker, client, Day.january(31, 2012)));
		Invoice invoice = createInvoice();

		assertEquals(new Euro(100), invoice.totalAmount());
		assertEquals(new Euro(21), invoice.vat());
		assertEquals(new Euro(121), invoice.toPay());
	}

	@Test
	public void twoEntryInvoice() {
		entries.add(new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(60)), worker, client, Day.january(31, 2012)));
		entries.add(new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(40)), worker, client, Day.january(31, 2012)));
		Invoice invoice = createInvoice();

		assertEquals(new Euro(100), invoice.totalAmount());
		assertEquals(new Euro(21), invoice.vat());
		assertEquals(new Euro(121), invoice.toPay());
	}

	@Test(expected = EntryNotForClientException.class)
	public void shouldThrowExceptionIfEntryIsNotForClient() {
		entries.add(new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(60)), worker, company, Day.january(31, 2012)));
		createInvoice();
	}

	@Test
	public void overdue() {
		Invoice invoice = createInvoice();
		assertFalse(invoice.isOverdue(Day.mars(31, 2012)));
		assertTrue(invoice.isOverdue(Day.april(1, 2012)));
	}

	protected Invoice createInvoice() {
		return new Invoice(company, client, Day.january(31, 2012), entries, 60);
	}
}

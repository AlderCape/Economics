package com.aldercape.internal.economics.model;

import static com.aldercape.internal.economics.model.CustomModelAsserts.assertClientEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ui.__TestObjectMother;

public class InvoiceBuilderTest {

	private Client company;
	private Client client;
	private SimpleInvoiceEntry validEntry;
	private InvoiceBuilder invoice;
	private Entry<Day> invalidEntry;

	@Before
	public void setUp() {
		__TestObjectMother objectMother = new __TestObjectMother();
		company = objectMother.myCompany();
		client = objectMother.otherCompany();
		validEntry = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(100)), objectMother.me(), client, Day.january(31, 2012));
		invalidEntry = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(100)), objectMother.me(), objectMother.myCompany(), Day.january(31, 2012));
		invoice = new InvoiceBuilder(company);
	}

	@Test
	public void simpleBuild() {
		assertFalse(invoice.isValid());
		invoice.forClient(client);
		assertFalse(invoice.isValid());
		invoice.issueDay(Day.january(31, 2012));
		assertFalse(invoice.isValid());
		invoice.daysToPay(10);
		assertFalse(invoice.isValid());
		invoice.addEntry(validEntry);
		assertTrue(invoice.isValid());
	}

	@Test
	public void chainedBuildWithOneEntry() {
		assertFalse(invoice.isValid());
		invoice.forClient(client).issued(Day.january(31, 2012)).with(60).daysToPay().andEntry(validEntry);
		assertTrue(invoice.isValid());
	}

	@Test
	public void chainedBuildWithTwoEntries() {
		assertFalse(invoice.isValid());
		invoice.forClient(client).issued(Day.january(31, 2012)).with(60).daysToPay().andEntry(validEntry).andEntry(validEntry);
		Invoice created = invoice.create();
		assertClientEquals(client, created.client());
		assertClientEquals(company, created.company());
		assertEquals(Day.january(31, 2012).daysAfter(60), created.dueDate());
		assertEquals(Day.january(31, 2012), created.issueDate());
		assertEquals(new Euro(200), created.totalAmount());
	}

	@Test(expected = EntryNotForClientException.class)
	public void chainedBuildWithInvalidEntryShouldThrowException() {
		invoice.forClient(client);
		invoice.andEntry(invalidEntry);
	}

	@Test(expected = EntryNotForClientException.class)
	public void chainedBuildWithInvalidEntryShouldThrowExceptionWhenClientIsSet() {
		invoice.andEntry(invalidEntry);
		invoice.forClient(client);
	}

	@Test
	public void chainedBuildWithInvalidEntryShouldNotThrowExceptionIfUserSpecifies() {
		invoice.filterInvalidEntries();
		invoice.forClient(client);
		invoice.andEntry(invalidEntry);
		assertTrue(invoice.entries().isEmpty());
	}

	@Test
	public void chainedBuildWithInvalidEntryShouldNotThrowExceptionWhenClientIsSetIfUserSpecifies() {
		invoice.filterInvalidEntries();
		invoice.andEntry(invalidEntry);
		invoice.forClient(client);
		assertTrue(invoice.entries().isEmpty());
	}

}

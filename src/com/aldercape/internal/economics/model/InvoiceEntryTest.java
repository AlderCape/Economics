package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ui.__TestObjectMother;

public class InvoiceEntryTest {

	private SimpleInvoiceEntry entry;
	private Client myCompany;

	@Before
	public void setUp() {
		__TestObjectMother objectMother = new __TestObjectMother();
		myCompany = objectMother.myCompany();
		Collaborator me = objectMother.me();
		entry = new SimpleInvoiceEntry(Unit.days(2), new Euro(50), me, myCompany, Day.january(1, 2011));
	}

	@Test
	public void shouldHaveBasicInformation() {
		assertEquals(new Euro(50), entry.rate());
		assertEquals(new Euro(100), entry.amount());
		assertEquals("Me", entry.collaborator().name());
		assertEquals("My Company", entry.client().name());
		assertEquals(Month.january(2011), entry.bookkeepingMonth());
		assertEquals(new Euro(21), entry.vat());
	}

}

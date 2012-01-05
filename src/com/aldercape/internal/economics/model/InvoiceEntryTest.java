package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class InvoiceEntryTest {

	private InvoiceEntry entry;

	@Before
	public void setUp() {
		entry = new InvoiceEntry(Unit.days(2), new Euro(50), new Colaborator("Johan"), new Client("My client"), Month.january(2011), Month.february(2011));
	}

	@Test
	public void shouldHaveBasicInformation() {
		assertEquals(new Euro(50), entry.rate());
		assertEquals(new Euro(100), entry.amount());
		assertEquals("Johan", entry.colaborator().name());
		assertEquals("My client", entry.client().name());
		assertEquals(Month.january(2011), entry.bookkeepingMonth());
		assertEquals(Month.february(2011), entry.cashflowMonth());
		assertEquals(new Euro(21), entry.vat());
	}

}

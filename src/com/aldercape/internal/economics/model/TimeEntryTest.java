package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TimeEntryTest {

	private Colaborator me;
	private Client myCompany;

	@Before
	public void setUp() {
		me = new Colaborator("Me");
		myCompany = new Client("My Company");
	}

	@Test
	public void test() {
		TimeEntry entry = new TimeEntry(Unit.days(1), new Euro(200), me, myCompany, Day.january(2, 2012));
		assertEquals(Day.january(2, 2012), entry.day());
		assertEquals(new Euro(200), entry.rate());
		assertEquals(Unit.days(1), entry.units());
		assertEquals("Me", entry.colaborator().name());
		assertEquals("My Company", entry.client().name());
		assertEquals(new Euro(200), entry.amount());
		assertEquals(new Euro(42), entry.vat());
	}

}

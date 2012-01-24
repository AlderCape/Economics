package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MonthTest {

	@Test
	public void shouldHaveAshortDisplayString() {
		Month month = new Month(MonthLiteral.January, 2011);
		assertEquals("January - 2011", month.shortDisplayString());
	}

	@Test
	public void valueObject() {
		Month month1a = new Month(MonthLiteral.January, 2011);
		Month month1b = new Month(MonthLiteral.January, 2011);
		Month month2a = new Month(MonthLiteral.January, 2010);
		Month month2b = new Month(MonthLiteral.February, 2011);

		assertEquals("January - 2011", month1a.toString());
		assertTrue("it self", month1a.equals(month1a));
		assertTrue("months with same month and year should be equal", month1a.equals(month1b));
		assertFalse("months with different years should not be equal", month1a.equals(month2a));
		assertFalse("months with different months should not be equal", month1a.equals(month2b));
		assertTrue("equal months should have same hash code", month1a.hashCode() == month1b.hashCode());
		assertFalse("shouldn't blow up when comparing to null", month1a.equals(null));
	}

	@Test
	public void shouldCreateForEachMonth() {
		assertEquals(new Month(MonthLiteral.January, 2011), Month.january(2011));
		assertEquals(new Month(MonthLiteral.February, 2011), Month.february(2011));
		assertEquals(new Month(MonthLiteral.Mars, 2011), Month.mars(2011));
		assertEquals(new Month(MonthLiteral.April, 2011), Month.april(2011));
		assertEquals(new Month(MonthLiteral.May, 2011), Month.may(2011));
		assertEquals(new Month(MonthLiteral.June, 2011), Month.june(2011));
		assertEquals(new Month(MonthLiteral.July, 2011), Month.july(2011));
		assertEquals(new Month(MonthLiteral.August, 2011), Month.august(2011));
		assertEquals(new Month(MonthLiteral.September, 2011), Month.september(2011));
		assertEquals(new Month(MonthLiteral.October, 2011), Month.october(2011));
		assertEquals(new Month(MonthLiteral.November, 2011), Month.november(2011));
		assertEquals(new Month(MonthLiteral.December, 2011), Month.december(2011));
	}

	@Test
	public void comparable() {
		assertTrue(Month.january(2011).compareTo(Month.february(2011)) < 0);
		assertTrue(Month.february(2011).compareTo(Month.mars(2011)) < 0);
		assertTrue(Month.february(2011).compareTo(Month.january(2011)) > 0);
		assertTrue(Month.january(2011).compareTo(Month.january(2012)) < 0);
		assertTrue(Month.january(2012).compareTo(Month.january(2011)) > 0);
	}

	@Test
	public void createFrom() {
		assertEquals(Month.february(2013), Month.createFrom(MonthLiteral.February, 2013));
	}
}

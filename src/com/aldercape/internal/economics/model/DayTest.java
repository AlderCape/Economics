package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DayTest {

	@Test
	public void shouldCreateForEachMonth() {
		assertEquals(new Day(1, Month.january(2011)), Day.january(1, 2011));
		assertEquals(new Day(1, Month.february(2011)), Day.february(1, 2011));
		assertEquals(new Day(1, Month.mars(2011)), Day.mars(1, 2011));
		assertEquals(new Day(1, Month.april(2011)), Day.april(1, 2011));
		assertEquals(new Day(1, Month.may(2011)), Day.may(1, 2011));
		assertEquals(new Day(1, Month.june(2011)), Day.june(1, 2011));
		assertEquals(new Day(1, Month.july(2011)), Day.july(1, 2011));
		assertEquals(new Day(1, Month.august(2011)), Day.august(1, 2011));
		assertEquals(new Day(1, Month.september(2011)), Day.september(1, 2011));
		assertEquals(new Day(1, Month.october(2011)), Day.october(1, 2011));
		assertEquals(new Day(1, Month.november(2011)), Day.november(1, 2011));
		assertEquals(new Day(1, Month.december(2011)), Day.december(1, 2011));
	}

	@Test
	public void comparable() {
		assertTrue(Day.january(1, 2011).compareTo(Day.february(1, 2011)) < 0);
		assertTrue(Day.february(1, 2011).compareTo(Day.mars(1, 2011)) < 0);
		assertTrue(Day.february(1, 2011).compareTo(Day.january(1, 2011)) > 0);
		assertTrue(Day.january(1, 2011).compareTo(Day.january(1, 2012)) < 0);
		assertTrue(Day.january(1, 2012).compareTo(Day.january(1, 2011)) > 0);
		assertTrue(Day.january(1, 2011).compareTo(Day.january(2, 2011)) < 0);
		assertTrue(Day.january(2, 2011).compareTo(Day.january(1, 2011)) > 0);
	}

	@Test
	public void valueObject() {
		Day day1a = Day.january(2, 2012);
		Day day1b = Day.january(2, 2012);
		Day day2a = Day.january(2, 2013);
		Day day2b = Day.january(3, 2012);
		Day day2c = Day.february(2, 2012);

		assertEquals("2 January - 2012", day1a.toString());
		assertTrue("should equal it self", day1a.equals(day1a));
		assertTrue("days with same values should be equal", day1a.equals(day1b));
		assertFalse("euros with different years should not be equal", day1a.equals(day2a));
		assertFalse("euros with different days should not be equal", day1a.equals(day2b));
		assertFalse("euros with different months should not be equal", day1a.equals(day2c));
		assertTrue("equal days should have same hash code", day1a.hashCode() == day1b.hashCode());
		assertFalse("shouldn't blow up when comparing to null", day1a.equals(null));
	}

}

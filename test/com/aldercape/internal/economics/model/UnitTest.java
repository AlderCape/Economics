package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UnitTest {

	@Test
	public void eightHoursIsOneDay() {
		assertEquals(Unit.hours(8), Unit.days(1));
		assertEquals(1, Unit.hours(8).days());
	}

	@Test
	public void valueObject() {
		Unit unit1a = Unit.days(10);
		Unit unit1b = Unit.days(10);
		Unit unit2a = Unit.days(11);

		assertEquals("10 days", unit1a.toString());
		assertTrue("it self", unit1a.equals(unit1a));
		assertTrue("units with same amount should be equal", unit1a.equals(unit1b));
		assertFalse("units with different amounts should not be equal", unit1a.equals(unit2a));
		assertTrue("units euros should have same hash code", unit1a.hashCode() == unit1b.hashCode());
		assertFalse("shouldn't blow up when comparing to null", unit1a.equals(null));
	}

}

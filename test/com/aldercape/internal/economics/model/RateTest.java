package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RateTest {

	@Test
	public void valueObject() {
		Rate rate1a = Rate.daily(100);
		Rate rate1b = Rate.daily(100);
		Rate rate2a = Rate.daily(200);

		assertEquals("(EUR) 100/day", rate1a.toString());
		assertTrue("it self", rate1a.equals(rate1a));
		assertTrue("rate with same amount should be equal", rate1a.equals(rate1b));
		assertFalse("months with different amount should not be equal", rate1a.equals(rate2a));
		assertTrue("equal rates should have same hash code", rate1a.hashCode() == rate1b.hashCode());
		assertFalse("shouldn't blow up when comparing to null", rate1a.equals(null));
	}

	@Test
	public void dailyRate() {
		Rate dailyRate = Rate.daily(new Euro(200));
		assertEquals(new Euro(200), dailyRate.costPerDay());
		assertEquals(new Euro(25), dailyRate.costPerHour());
	}

	@Test
	public void dailyRateWithPrimitive() {
		Rate dailyRate = Rate.daily(200);
		assertEquals(new Euro(200), dailyRate.costPerDay());
		assertEquals(new Euro(25), dailyRate.costPerHour());
	}

	@Test
	public void hourlyRate() {
		Rate dailyRate = Rate.hourly(new Euro(25));
		assertEquals(new Euro(200), dailyRate.costPerDay());
		assertEquals(new Euro(25), dailyRate.costPerHour());
	}

}

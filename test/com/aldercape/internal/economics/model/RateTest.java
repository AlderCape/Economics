package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RateTest {

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

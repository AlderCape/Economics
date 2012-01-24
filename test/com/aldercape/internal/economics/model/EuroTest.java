package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EuroTest {

	@Test
	public void shouldHaveMultiply() {
		assertEquals(new Euro(20), new Euro(10).times(2));
	}

	@Test
	public void valueObject() {
		Euro euro1a = new Euro(10);
		Euro euro1b = new Euro(10);
		Euro euro2a = new Euro(11);

		assertEquals("Û 10", euro1a.toString());
		assertTrue("should equal it self", euro1a.equals(euro1a));
		assertTrue("euros with same amount should be equal", euro1a.equals(euro1b));
		assertFalse("euros with different amounts should not be equal", euro1a.equals(euro2a));
		assertTrue("equal euros should have same hash code", euro1a.hashCode() == euro1b.hashCode());
		assertFalse("shouldn't blow up when comparing to null", euro1a.equals(null));
	}

	@Test
	public void percentage() {
		assertEquals(new Euro(20), new Euro(100).percentage(20));
	}

	@Test
	public void plus() {
		assertEquals(new Euro(20), new Euro(15).plus(new Euro(5)));
	}

	@Test
	public void canBeCreatedFromString() {
		assertEquals(new Euro(42), Euro.createFrom(42));
	}
}

package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.aldercape.internal.economics.model.Euro;

public class RateFieldTest {

	@Test
	public void textReflectsEuroAmountUponConstruction() {
		RateField textField = new RateField(new Euro(42));
		assertEquals((Integer) 42, textField.getValue());
	}

	@Test
	public void canRetreiveAmount() {
		RateField textField = new RateField(new Euro(42));
		assertEquals(new Euro(42), textField.getEuros());
	}

	@Test
	public void changingTextChangesAmount() {
		RateField textField = new RateField(new Euro(42));
		textField.setValue(100);
		assertEquals(new Euro(100), textField.getEuros());
	}
}

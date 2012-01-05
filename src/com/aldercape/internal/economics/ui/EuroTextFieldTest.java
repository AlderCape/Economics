package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.aldercape.internal.economics.model.Euro;

public class EuroTextFieldTest {

	@Test
	public void textReflectsEuroAmountUponConstruction() {
		EuroTextField textField = new EuroTextField(new Euro(42));
		assertEquals("42", textField.getText());
	}

	@Test
	public void canRetreiveAmount() {
		EuroTextField textField = new EuroTextField(new Euro(42));
		assertEquals(new Euro(42), textField.getEuros());
	}

	@Test
	public void changingTextChangesAmount() {
		EuroTextField textField = new EuroTextField(new Euro(42));
		textField.setDisplayText("100");
		assertEquals(new Euro(100), textField.getEuros());
	}
}

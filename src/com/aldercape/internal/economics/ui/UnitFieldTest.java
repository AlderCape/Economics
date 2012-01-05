package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Unit;

public class UnitFieldTest {

	private UnitTextField textField;

	@Before
	public void setUp() {
		textField = new UnitTextField(Unit.days(16));
	}

	@Test
	public void textReflectsEuroAmountUponConstruction() {
		assertEquals("16", textField.getText());
	}

	@Test
	public void canRetreiveAmount() {
		assertEquals(Unit.days(16), textField.getUnits());
	}

	@Test
	public void changingTextChangesAmount() {
		textField.setDisplayText("100");
		assertEquals(Unit.days(100), textField.getUnits());
	}

	@Test
	public void emptyConsutructorShouldHave0days() {
		UnitTextField textField = new UnitTextField();
		assertEquals(Unit.days(0), textField.getUnits());
	}

}

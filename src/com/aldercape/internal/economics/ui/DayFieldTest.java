package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.MonthLiteral;

public class DayFieldTest {

	private DayTextField field;
	private Component[] components;

	@Before
	public void setUp() {
		field = new DayTextField(Day.february(1, 2011));
		components = field.getComponents();
	}

	@Test
	public void layout() {
		assertEquals("# of components", 3, components.length);
		assertEquals(BoxLayout.class, field.getLayout().getClass());
		assertEquals(BoxLayout.X_AXIS, ((BoxLayout) field.getLayout()).getAxis());
		assertEquals(JComboBox.class, components[0].getClass());
		assertEquals(IntTextField.class, components[1].getClass());
		assertEquals(IntTextField.class, components[2].getClass());
	}

	@Test
	public void comboboxShouldHaveAllMonths() {
		JComboBox monthCombo = getMonthComboBox();
		assertEquals(12, monthCombo.getModel().getSize());
		assertEquals("valus should be month enumeration", MonthLiteral.class, monthCombo.getModel().getElementAt(0).getClass());
	}

	private JComboBox getMonthComboBox() {
		return (JComboBox) components[0];
	}

	// @Test
	// public void textReflectsMonthUponConstruction() {
	// assertEquals("2011", field.getText());
	// assertEquals(MonthLiteral.February, field.getSelectedItem());
	// }

	@Test
	public void canRetreiveDay() {
		assertEquals(Day.february(1, 2011), field.createDomainObject());
	}

	// @Test
	// public void changingValuesChangesComboBoxAndField() {
	// field.setSelectedItem(MonthLiteral.Mars);
	// field.setDisplayText("1, 2012");
	// assertEquals("2012", field.getText());
	// assertEquals(MonthLiteral.Mars, field.getSelectedItem());
	// }
	//
	// @Test
	// public void changingTextChangesAmount() {
	// field.setDisplayText("1, 2013");
	// field.setSelectedItem(MonthLiteral.December);
	// assertEquals(Month.december(2013), field.getDay());
	// }

}

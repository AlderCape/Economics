package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Month;
import com.aldercape.internal.economics.model.MonthLiteral;

public class MonthFieldTest {

	private MonthField field;
	private Component[] components;

	@Before
	public void setUp() {
		field = new MonthField(Month.february(2011));
		components = field.getComponents();
	}

	@Test
	public void layout() {
		assertEquals("# of components", 2, components.length);
		assertEquals(BoxLayout.class, field.getLayout().getClass());
		assertEquals(BoxLayout.X_AXIS, ((BoxLayout) field.getLayout()).getAxis());
		assertEquals(JComboBox.class, components[0].getClass());
		assertEquals(JTextField.class, components[1].getClass());
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

	@Test
	public void textReflectsMonthUponConstruction() {
		assertEquals("2011", field.getText());
		assertEquals(MonthLiteral.February, field.getSelectedItem());
	}

	@Test
	public void canRetreiveMonth() {
		assertEquals(Month.february(2011), field.getMonth());
	}

	@Test
	public void changingValuesChangesComboBoxAndField() {
		field.setSelectedItem(MonthLiteral.Mars);
		field.setDisplayText("2012");
		assertEquals("2012", field.getText());
		assertEquals(MonthLiteral.Mars, field.getSelectedItem());
	}

	@Test
	public void changingTextChangesAmount() {
		field.setDisplayText("2013");
		field.setSelectedItem(MonthLiteral.December);
		assertEquals(Month.december(2013), field.getMonth());
	}

}

package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class CustomUiAsserts {

	public static void assertFormColaboratorField(String message, Component label, Component field, JPanel panel) {
		CustomUiAsserts.assertFormField(message, label, ColaboratorField.class, field, panel);
	}

	public static void assertFormClientField(String message, Component label, Component field, JPanel panel) {
		CustomUiAsserts.assertFormField(message, label, ClientField.class, field, panel);
	}

	public static void assertFormUnitField(String message, Component label, Component field, JPanel panel) {
		CustomUiAsserts.assertFormField(message, label, UnitTextField.class, field, panel);
	}

	public static void assertFormEuroField(String message, Component label, Component field, JPanel panel) {
		CustomUiAsserts.assertFormField(message, label, EuroTextField.class, field, panel);
	}

	public static void assertFormDayField(String message, Component label, Component field, JPanel panel) {
		CustomUiAsserts.assertFormField(message, label, DayTextField.class, field, panel);
	}

	public static void assertFormField(String message, Component label, Class<? extends JComponent> expectedFieldClass, Component field, JPanel panel) {
		assertEquals(message + " label class", JLabel.class, label.getClass());
		assertEquals(message + " field class", expectedFieldClass, field.getClass());
		assertNull(message + " constraints", ((MigLayout) panel.getLayout()).getComponentConstraints(field));
	}

}

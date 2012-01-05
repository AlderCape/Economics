package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.ApplicationModel;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Colaborator;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.model.Month;
import com.aldercape.internal.economics.model.Unit;

public class InvoiceEntryPanelTest {
	private InvoiceEntryPanel panel;
	private Ledger ledger;
	private ApplicationModel model;

	@Before
	public void setUp() {
		ledger = new Ledger();
		model = new ApplicationModel(ledger);
		panel = new InvoiceEntryPanel(model);
	}

	@Test
	public void layout() {
		assertEquals("layout", MigLayout.class, panel.getLayout().getClass());
		MigLayout layout = (MigLayout) panel.getLayout();
		assertEquals("panel constraints", "wrap 4", layout.getLayoutConstraints());
		assertEquals("colunm constraints", "[right]rel[left,grow,fill][right]rel[left,grow,fill]", layout.getColumnConstraints());

		Component[] components = panel.getComponents();
		assertEquals("# of components", 12, components.length);
		assertFormUnitField("Unit", components[0], components[1]);
		assertFormEuroField("Rate", components[2], components[3]);
		assertFormColaboratorField("Person", components[4], components[5]);
		assertFormClientField("Client", components[6], components[7]);
		assertFormMonthField("Bookkeeping month", components[8], components[9]);
		assertFormMonthField("Cashflow month", components[10], components[11]);
	}

	@Test
	public void addEntryShouldCreateANewIncomeEntryAndAddItToApplicationModel() {
		assertEquals(0, ledger.numberOfEntries());
		InvoiceEntry populateWith = new InvoiceEntry(Unit.days(19), new Euro(250), new Colaborator("Other"), new Client("Second client"), Month.january(2011), Month.february(2011));
		panel.setEntry(populateWith);
		panel.addEntry();
		assertEquals(1, ledger.numberOfEntries());
		InvoiceEntry entry = ledger.entry(0);
		assertEquals(populateWith.units(), entry.units());
		assertEquals(populateWith.rate(), entry.rate());
		assertEquals(populateWith.colaborator().name(), entry.colaborator().name());
		assertEquals(populateWith.client().name(), entry.client().name());
		assertEquals(populateWith.bookkeepingMonth(), entry.bookkeepingMonth());
		assertEquals(populateWith.cashflowMonth(), entry.cashflowMonth());

	}

	private void assertFormColaboratorField(String message, Component label, Component field) {
		assertFormField(message, label, ColaboratorTextField.class, field);
	}

	private void assertFormClientField(String message, Component label, Component field) {
		assertFormField(message, label, ClientTextField.class, field);
	}

	private void assertFormMonthField(String message, Component label, Component field) {
		assertFormField(message, label, MonthField.class, field);
	}

	private void assertFormUnitField(String message, Component label, Component field) {
		assertFormField(message, label, UnitTextField.class, field);
	}

	private void assertFormEuroField(String message, Component label, Component field) {
		assertFormField(message, label, EuroTextField.class, field);
	}

	private void assertFormField(String message, Component label, Class<? extends JComponent> expectedFieldClass, Component field) {
		assertEquals(message + " label class", JLabel.class, label.getClass());
		assertEquals(message + " field class", expectedFieldClass, field.getClass());
		assertNull(message + " constraints", ((MigLayout) panel.getLayout()).getComponentConstraints(field));
	}

}

package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Component;

import net.miginfocom.swing.MigLayout;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.ApplicationModel;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Colaborator;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.Unit;

public class TimeEntryPanelTest {
	private TimeEntryPanel panel;
	private Ledger ledger;
	private ApplicationModel model;

	@Before
	public void setUp() {
		ledger = new Ledger();
		model = new ApplicationModel(ledger);
		panel = new TimeEntryPanel(model);
	}

	@Test
	public void layout() {
		assertEquals("layout", MigLayout.class, panel.getLayout().getClass());
		MigLayout layout = (MigLayout) panel.getLayout();
		assertEquals("panel constraints", "wrap 4", layout.getLayoutConstraints());
		assertEquals("colunm constraints", "[right]rel[left,grow,fill][right]rel[left,grow,fill]", layout.getColumnConstraints());

		Component[] components = getComponents();
		assertEquals("# of components", 10, components.length);
		CustomUiAsserts.assertFormUnitField("Unit", components[0], components[1], panel);
		CustomUiAsserts.assertFormEuroField("Rate", components[2], components[3], panel);
		CustomUiAsserts.assertFormColaboratorField("Person", components[4], components[5], panel);
		CustomUiAsserts.assertFormClientField("Client", components[6], components[7], panel);
		CustomUiAsserts.assertFormDayField("Day", components[8], components[9], panel);
	}

	private Component[] getComponents() {
		return panel.getComponents();
	}

	@Test
	public void addEntryShouldCreateANewIncomeEntryAndAddItToApplicationModel() {
		assertEquals(0, ledger.numberOfEntries());
		TimeEntry populateWith = new TimeEntry(Unit.days(19), new Euro(250), new Colaborator("Other"), new Client("Second client"), Day.january(1, 2011));
		panel.setEntry(populateWith);
		panel.addEntry();
		Entry<Day> addedEntry = ledger.entry(0);
		assertNotNull(addedEntry);
		assertEquals(populateWith.units(), addedEntry.units());
		assertEquals(populateWith.rate(), addedEntry.rate());
		assertEquals(populateWith.colaborator().name(), addedEntry.colaborator().name());
		assertEquals(populateWith.client().name(), addedEntry.client().name());
		assertEquals(populateWith.day(), addedEntry.getTimePoint());
	}

}
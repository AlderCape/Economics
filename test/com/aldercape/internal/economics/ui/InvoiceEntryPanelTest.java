package com.aldercape.internal.economics.ui;

import static com.aldercape.internal.economics.model.CustomModelAsserts.assertInvoiceEntryEquals;
import static org.junit.Assert.assertEquals;

import java.awt.Component;

import net.miginfocom.swing.MigLayout;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.SimpleInvoiceEntry;
import com.aldercape.internal.economics.model.Unit;

public class InvoiceEntryPanelTest {
	private InvoiceEntryPanel panel;
	private Ledger ledger;
	private ApplicationModel model;
	private Collaborator me;
	private Client myCompany;

	@Before
	public void setUp() {
		__TestObjectMother objectMother = new __TestObjectMother();
		ledger = new Ledger();
		model = new ApplicationModel(ledger);
		panel = new InvoiceEntryPanel(model);
		me = objectMother.me();
		myCompany = objectMother.myCompany();
	}

	@Test
	public void layout() {
		assertEquals("layout", MigLayout.class, panel.getLayout().getClass());
		MigLayout layout = (MigLayout) panel.getLayout();
		assertEquals("panel constraints", "wrap 4", layout.getLayoutConstraints());
		assertEquals("colunm constraints", "[right]rel[left,grow,fill][right]rel[left,grow,fill]", layout.getColumnConstraints());

		Component[] components = panel.getComponents();
		assertEquals("# of components", 10, components.length);
		CustomUiAsserts.assertFormUnitField("Unit", components[0], components[1], panel);
		CustomUiAsserts.assertFormEuroField("Rate", components[2], components[3], panel);
		CustomUiAsserts.assertFormCollaboratorField("Person", components[4], components[5], panel);
		CustomUiAsserts.assertFormClientField("Client", components[6], components[7], panel);
		CustomUiAsserts.assertFormDayField("Bookkeeping month", components[8], components[9], panel);
	}

	@Test
	public void addEntryShouldCreateANewIncomeEntryAndAddItToApplicationModel() {
		assertEquals(0, ledger.numberOfEntries());
		SimpleInvoiceEntry populateWith = new SimpleInvoiceEntry(Unit.days(19), Rate.daily(new Euro(250)), me, myCompany, Day.january(1, 2011));
		panel.setEntry(populateWith);
		panel.addEntry();
		assertEquals(1, ledger.numberOfEntries());
		Entry<Day> entry = ledger.entry(0);
		assertInvoiceEntryEquals(populateWith, (SimpleInvoiceEntry) entry);

	}

}

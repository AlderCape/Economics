package com.aldercape.internal.economics.ui;

import static com.aldercape.internal.economics.model.CustomModelAsserts.assertClientEquals;
import static org.junit.Assert.assertEquals;

import java.awt.Component;

import net.miginfocom.swing.MigLayout;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Ledger;

public class ClientPanelTest {

	private ClientPanel panel;
	private ApplicationModel model;

	@Before
	public void setUp() {
		model = new ApplicationModel(new Ledger());
		panel = new ClientPanel(model);
	}

	@Test
	public void layout() {
		assertEquals(MigLayout.class, panel.getLayout().getClass());

		Component[] components = panel.getComponents();
		assertEquals("# of components", 2, components.length);
		CustomUiAsserts.assertFormStringField("Name", components[0], components[1], panel);
	}

	@Test
	public void addEntryShouldCreateANewIncomeEntryAndAddItToApplicationModel() {
		Client populateWith = new __TestObjectMother().myCompany();
		panel.setEntry(populateWith);
		assertEquals(0, model.getClientRepository().getAll().size());
		panel.addEntry();
		assertEquals(1, model.getClientRepository().getAll().size());
		Client entry = model.getClientRepository().getAll().get(0);
		assertClientEquals(populateWith, entry);

	}

}

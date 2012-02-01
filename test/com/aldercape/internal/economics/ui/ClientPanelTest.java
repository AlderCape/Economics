package com.aldercape.internal.economics.ui;

import static com.aldercape.internal.economics.model.CustomModelAsserts.*;
import static org.junit.Assert.*;

import java.awt.Component;

import net.miginfocom.swing.MigLayout;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.persistence.InMemoryClientRepository;

public class ClientPanelTest {

	private ClientPanel panel;
	private ApplicationModel model;

	@Before
	public void setUp() {
		model = new ApplicationModel(new Ledger());
		model.setClientRepository(new InMemoryClientRepository());
		panel = new ClientPanel(model);
	}

	@Test
	public void layout() {
		assertEquals(MigLayout.class, panel.getLayout().getClass());

		Component[] components = panel.getComponents();
		assertEquals("# of components", 14, components.length);
		CustomUiAsserts.assertFormStringField("Name", components[0], components[1], panel);
		CustomUiAsserts.assertFormStringField("Contact person", components[2], components[3], panel);
		CustomUiAsserts.assertFormStringField("Street", components[4], components[5], panel);
		CustomUiAsserts.assertFormStringField("Number", components[6], components[7], panel);
		CustomUiAsserts.assertFormStringField("Zip code", components[8], components[9], panel);
		CustomUiAsserts.assertFormStringField("City", components[10], components[11], panel);
		CustomUiAsserts.assertFormStringField("Vat number", components[12], components[13], panel);
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

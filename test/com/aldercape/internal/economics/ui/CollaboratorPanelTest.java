package com.aldercape.internal.economics.ui;

import static com.aldercape.internal.economics.model.CustomModelAsserts.*;
import static org.junit.Assert.*;

import java.awt.Component;

import net.miginfocom.swing.MigLayout;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.persistence.InMemoryCollaboratorRepository;

public class CollaboratorPanelTest {

	private CollaboratorPanel panel;
	private ApplicationModel model;

	@Before
	public void setUp() {
		model = new ApplicationModel(new Ledger());
		model.setCollaboraorRepository(new InMemoryCollaboratorRepository());
		panel = new CollaboratorPanel(model);
	}

	@Test
	public void layout() {
		assertEquals(MigLayout.class, panel.getLayout().getClass());

		Component[] components = panel.getComponents();
		assertEquals("# of components", 6, components.length);
		CustomUiAsserts.assertFormStringField("First name", components[0], components[1], panel);
		CustomUiAsserts.assertFormStringField("Last name", components[2], components[3], panel);
		CustomUiAsserts.assertFormStringField("Email", components[4], components[5], panel);
	}

	@Test
	public void addEntryShouldCreateANewIncomeEntryAndAddItToApplicationModel() {
		Collaborator populateWith = new Collaborator("Johan", "Aludden", "me@email.com");
		panel.setEntry(populateWith);
		assertEquals(0, model.getCollaboratorRepository().getAll().size());
		panel.addEntry();
		assertEquals(1, model.getCollaboratorRepository().getAll().size());
		Collaborator entry = model.getCollaboratorRepository().getAll().get(0);
		assertCollaboratorEquals(populateWith, entry);

	}

}

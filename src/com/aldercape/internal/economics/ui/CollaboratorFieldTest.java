package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;

import javax.swing.JComboBox;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.persistence.InMemoryCollaboratorRepository;

public class CollaboratorFieldTest {

	private CollaboratorField collaboratorField;
	private CollaboratorRepository collaboratorRepository;

	@Before
	public void setUp() {
		collaboratorRepository = new InMemoryCollaboratorRepository();
		collaboratorField = new CollaboratorField(collaboratorRepository);
	}

	@Test
	public void layout() {
		assertEquals(JComboBox.class, collaboratorField.getClass().getSuperclass());
	}

	@Test
	public void modelShouldReflectClientRepsitory() {
		assertEquals(0, collaboratorField.getModel().getSize());
		collaboratorRepository.add(new __TestObjectMother().me());
		assertEquals(1, collaboratorField.getModel().getSize());
	}

}

package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;

import javax.swing.JComboBox;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.persistence.InMemoryColaboratorRepository;

public class ColaboratorFieldTest {

	private CollaboratorField colaboratorField;
	private CollaboratorRepository colaboratorRepository;

	@Before
	public void setUp() {
		colaboratorRepository = new InMemoryColaboratorRepository();
		colaboratorField = new CollaboratorField(colaboratorRepository);
	}

	@Test
	public void layout() {
		assertEquals(JComboBox.class, colaboratorField.getClass().getSuperclass());
	}

	@Test
	public void modelShouldReflectClientRepsitory() {
		assertEquals(0, colaboratorField.getModel().getSize());
		colaboratorRepository.add(new __TestObjectMother().me());
		assertEquals(1, colaboratorField.getModel().getSize());
	}

}

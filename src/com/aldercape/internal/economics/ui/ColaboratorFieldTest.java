package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;

import javax.swing.JComboBox;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.ColaboratorRepository;
import com.aldercape.internal.economics.persistence.InMemoryColaboratorRepository;

public class ColaboratorFieldTest {

	private ColaboratorField colaboratorField;
	private ColaboratorRepository colaboratorRepository;

	@Before
	public void setUp() {
		colaboratorRepository = new InMemoryColaboratorRepository();
		colaboratorField = new ColaboratorField(colaboratorRepository);
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

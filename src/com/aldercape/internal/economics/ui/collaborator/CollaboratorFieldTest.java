package com.aldercape.internal.economics.ui.collaborator;

import static org.junit.Assert.assertEquals;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.persistence.InMemoryCollaboratorRepository;
import com.aldercape.internal.economics.ui.__TestObjectMother;

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

	@Test
	public void shouldRenderTheFullName() {
		Collaborator myCompany = new __TestObjectMother().me();
		Component rendered = collaboratorField.getRenderer().getListCellRendererComponent(new JList<Collaborator>(), myCompany, 0, false, false);
		assertEquals(JLabel.class, rendered.getClass());
		assertEquals(myCompany.fullname(), ((JLabel) rendered).getText());
		rendered = collaboratorField.getRenderer().getListCellRendererComponent(new JList<Collaborator>(), null, 0, false, false);
		assertEquals("", ((JLabel) rendered).getText());
	}
}

package com.aldercape.internal.economics.ui.client;

import static org.junit.Assert.assertEquals;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.persistence.InMemoryClientRepository;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class ClientFieldTest {

	private ClientField clientTextField;
	private ClientRepository clientRepository;

	@Before
	public void setUp() {
		clientRepository = new InMemoryClientRepository();
		clientTextField = new ClientField(clientRepository);
	}

	@Test
	public void layout() {
		assertEquals(JComboBox.class, clientTextField.getClass().getSuperclass());
	}

	@Test
	public void modelShouldReflectClientRepsitory() {
		assertEquals(0, clientTextField.getModel().getSize());
		clientRepository.add(new __TestObjectMother().myCompany());
		assertEquals(1, clientTextField.getModel().getSize());
	}

	@Test
	public void shouldRenderTheName() {
		Client myCompany = new __TestObjectMother().myCompany();
		Component rendered = clientTextField.getRenderer().getListCellRendererComponent(new JList<Client>(), myCompany, 0, false, false);
		assertEquals(JLabel.class, rendered.getClass());
		assertEquals(myCompany.name(), ((JLabel) rendered).getText());
		rendered = clientTextField.getRenderer().getListCellRendererComponent(new JList<Client>(), null, 0, false, false);
		assertEquals("", ((JLabel) rendered).getText());
	}
}

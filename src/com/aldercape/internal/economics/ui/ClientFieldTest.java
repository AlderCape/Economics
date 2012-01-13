package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;

import javax.swing.JComboBox;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.persistence.InMemoryClientRepository;

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

}

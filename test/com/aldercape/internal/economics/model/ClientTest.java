package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClientTest {

	@Test
	public void creation() {
		Address address = new Address("street name", "99a", "20100", "Milano");
		Client client = new Client("Client name", address, "p.iva", "contact person");

		assertEquals("Client name", client.name());
		assertEquals("p.iva", client.vatNumber());
		assertEquals("street name", client.streetName());
		assertEquals("99a", client.streetNumber());
		assertEquals("20100", client.zipcode());
		assertEquals("Milano", client.city());
		assertEquals("contact person", client.contactPerson());
	}
}

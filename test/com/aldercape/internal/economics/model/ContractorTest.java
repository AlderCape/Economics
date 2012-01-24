package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ContractorTest {

	@Test
	public void creation() {
		Contractor contractor = new Contractor("firstname", "lastname");
		assertEquals("firstname", contractor.firstname());
		assertEquals("lastname", contractor.lastname());
		assertEquals("firstname lastname", contractor.fullname());
	}

}

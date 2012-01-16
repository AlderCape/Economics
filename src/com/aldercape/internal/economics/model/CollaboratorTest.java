package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CollaboratorTest {

	@Test
	public void test() {
		Collaborator collaborator = new Collaborator("Firstname", "Lastname");
		assertEquals("Firstname Lastname", collaborator.fullname());
		assertEquals("Firstname", collaborator.firstname());
		assertEquals("Lastname", collaborator.lastname());
	}

}

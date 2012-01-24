package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aldercape.internal.economics.ui.__TestObjectMother;

public class CollaboratorTest {

	@Test
	public void test() {
		Collaborator collaborator = new Collaborator("Firstname", "Lastname");
		assertEquals("Firstname Lastname", collaborator.fullname());
		assertEquals("Firstname", collaborator.firstname());
		assertEquals("Lastname", collaborator.lastname());
	}

	@Test
	public void comparableOnFullName() {
		__TestObjectMother objectMother = new __TestObjectMother();
		Collaborator me = objectMother.me();
		Collaborator other = objectMother.other();
		assertEquals(0, me.compareTo(me));
		assertTrue(me.compareTo(other) < 0);
		assertTrue(other.compareTo(me) > 0);
	}

}

package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ColaboratorTest {

	@Test
	public void test() {
		Collaborator colaborator = new Collaborator("Name");
		assertEquals("Name", colaborator.name());
	}

}

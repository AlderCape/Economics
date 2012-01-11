package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ColaboratorTest {

	@Test
	public void test() {
		Colaborator colaborator = new Colaborator("Name");
		assertEquals("Name", colaborator.name());

	}

}

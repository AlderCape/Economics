package com.aldercape.internal.economics.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProjectTest {

	@Test
	public void test() {
		Project project = new Project("Project name");
		assertEquals("Project name", project.name());
	}

}

package com.aldercape.internal.economics.model;

import static com.aldercape.internal.economics.model.CustomModelAsserts.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.aldercape.internal.economics.ui.__TestObjectMother;

public class ProjectTest {

	@Test
	public void test() {
		__TestObjectMother objectMother = new __TestObjectMother();
		Project project = new Project("Project name", objectMother.otherCompany());
		project.addMember(objectMother.me(), Rate.daily(new Euro(200)));
		assertEquals("Project name", project.name());
		assertClientEquals(objectMother.otherCompany(), project.client());
		assertEquals(1, project.memberCount());
	}
}

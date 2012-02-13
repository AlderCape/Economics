package com.aldercape.internal.economics.model;

import static com.aldercape.internal.economics.model.CustomModelAsserts.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ui.__TestObjectMother;

public class ProjectTest {

	private __TestObjectMother objectMother;
	private Project project;

	@Before
	public void setUp() {
		objectMother = new __TestObjectMother();
		project = new Project("Project name", objectMother.otherCompany());
	}

	@Test
	public void projectWithOneMember() {
		project.addMember(objectMother.me(), Rate.daily(new Euro(200)));
		assertEquals("Project name", project.name());
		assertClientEquals(objectMother.otherCompany(), project.client());
		assertEquals(1, project.memberCount());
		assertCollaboratorEquals(objectMother.me(), project.getMember(0).getCollaborator());
		assertEquals(Rate.daily(new Euro(200)), project.getMember(0).rate());
	}

	@Test
	public void projectWithTwoMembers() {
		project.addMember(objectMother.me(), Rate.daily(new Euro(200)));
		project.addMember(objectMother.other(), Rate.daily(new Euro(200)));

		assertEquals(2, project.memberCount());
		assertCollaboratorEquals(objectMother.me(), project.getMember(0).getCollaborator());
		assertEquals(Rate.daily(new Euro(200)), project.getMember(0).rate());

		assertCollaboratorEquals(objectMother.other(), project.getMember(1).getCollaborator());
		assertEquals(Rate.daily(new Euro(200)), project.getMember(1).rate());
	}
}

package com.aldercape.internal.economics.model;

import static com.aldercape.internal.economics.model.CustomModelAsserts.assertCollaboratorEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ui.__TestObjectMother;

public class TimeEntryTest {

	private Collaborator me;
	private Client myCompany;

	@Before
	public void setUp() {
		__TestObjectMother objectMother = new __TestObjectMother();
		me = objectMother.me();
		myCompany = objectMother.myCompany();
	}

	@Test
	public void test() {
		TimeEntry entry = new TimeEntry(Unit.days(1), new Euro(200), me, myCompany, Day.january(2, 2012));
		assertEquals(Day.january(2, 2012), entry.day());
		assertEquals(new Euro(200), entry.rate());
		assertEquals(Unit.days(1), entry.units());
		assertCollaboratorEquals(me, entry.collaborator());
		assertEquals("My Company", entry.client().name());
		assertEquals(new Euro(200), entry.amount());
		assertEquals(new Euro(42), entry.vat());
	}

}

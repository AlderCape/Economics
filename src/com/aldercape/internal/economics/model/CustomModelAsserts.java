package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;

public class CustomModelAsserts {

	public static void assertInvoiceEntryEquals(InvoiceEntry populateWith, InvoiceEntry entry) {
		assertEquals(populateWith.units(), entry.units());
		assertEquals(populateWith.rate(), entry.rate());
		assertEquals(populateWith.collaborator().name(), entry.collaborator().name());
		assertEquals(populateWith.client().name(), entry.client().name());
		assertEquals(populateWith.getTimePoint(), entry.getTimePoint());
	}

	public static void assertTimeEntryEquals(TimeEntry expected, TimeEntry actual) {
		assertEquals(expected.units(), actual.units());
		assertEquals(expected.rate(), actual.rate());
		assertEquals(expected.collaborator().name(), actual.collaborator().name());
		assertEquals(expected.client().name(), actual.client().name());
		assertEquals(expected.getTimePoint(), actual.getTimePoint());
	}

	public static void assertClientEquals(Client expected, Client actual) {
		assertEquals(expected.name(), actual.name());
	}

	public static void assertCollaboratorEquals(Collaborator expected, Collaborator actual) {
		assertEquals(expected.name(), actual.name());
	}

}

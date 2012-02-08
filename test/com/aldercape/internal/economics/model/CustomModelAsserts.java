package com.aldercape.internal.economics.model;

import static org.junit.Assert.*;

import java.util.List;

public class CustomModelAsserts {

	public static void assertInvoiceEntryEquals(InvoiceEntry populateWith, InvoiceEntry entry) {
		assertEquals(populateWith.units(), entry.units());
		assertEquals(populateWith.rate(), entry.rate());
		assertEquals(populateWith.collaborator().fullname(), entry.collaborator().fullname());
		assertEquals(populateWith.client().name(), entry.client().name());
		assertEquals(populateWith.getTimePoint(), entry.getTimePoint());
	}

	public static void assertInvoiceEquals(Invoice expected, Invoice actual) {
		assertClientEquals(expected.client(), actual.client());
		assertClientEquals(expected.company(), actual.company());
		assertEquals(expected.dueDate(), actual.dueDate());
		List<Entry<Day>> expectedEntries = expected.entries();
		List<Entry<Day>> actualEntries = actual.entries();
		assertEquals(expectedEntries.size(), actualEntries.size());
		for (int i = 0; i < expectedEntries.size(); i++) {
			assertInvoiceEntryEquals((InvoiceEntry) expectedEntries.get(i), (InvoiceEntry) actualEntries.get(i));
		}

		assertEquals(expected.issueDate(), actual.issueDate());
	}

	public static void assertTimeEntryEquals(TimeEntry expected, TimeEntry actual) {
		assertEquals(expected.units(), actual.units());
		assertEquals(expected.rate(), actual.rate());
		assertEquals(expected.collaborator().fullname(), actual.collaborator().fullname());
		assertEquals(expected.client().name(), actual.client().name());
		assertEquals(expected.getTimePoint(), actual.getTimePoint());
	}

	public static void assertClientEquals(Client expected, Client actual) {
		assertEquals(expected.name(), actual.name());
		assertEquals(expected.city(), actual.city());
		assertEquals(expected.contactPerson(), actual.contactPerson());
		assertEquals(expected.streetName(), actual.streetName());
		assertEquals(expected.streetNumber(), actual.streetNumber());
		assertEquals(expected.vatNumber(), actual.vatNumber());
		assertEquals(expected.zipcode(), actual.zipcode());
	}

	public static void assertCollaboratorEquals(Collaborator expected, Collaborator actual) {
		assertEquals(expected.fullname(), actual.fullname());
		assertEquals(expected.firstname(), actual.firstname());
		assertEquals(expected.lastname(), actual.lastname());
		assertEquals(expected.email(), actual.email());
	}

}

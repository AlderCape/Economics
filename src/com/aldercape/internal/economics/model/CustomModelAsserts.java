package com.aldercape.internal.economics.model;

import static org.junit.Assert.assertEquals;

public class CustomModelAsserts {

	public static void assertInvoiceEntryEquals(InvoiceEntry populateWith, InvoiceEntry entry) {
		assertEquals(populateWith.units(), entry.units());
		assertEquals(populateWith.rate(), entry.rate());
		assertEquals(populateWith.colaborator().name(), entry.colaborator().name());
		assertEquals(populateWith.client().name(), entry.client().name());
		assertEquals(populateWith.getTimePoint(), entry.getTimePoint());
	}

}

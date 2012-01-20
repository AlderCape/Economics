package com.aldercape.internal.economics.model;

import static com.aldercape.internal.economics.model.CustomModelAsserts.assertClientEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.aldercape.internal.economics.ui.__TestObjectMother;

public class InvoiceTest {

	@Test
	public void emptyInvoice() {
		Invoice invoice = new Invoice(new __TestObjectMother().myCompany(), Day.january(31, 2012));
		assertClientEquals(new __TestObjectMother().myCompany(), invoice.client());
		assertEquals(Day.january(31, 2012), invoice.issueDate());
		assertEquals(new Euro(0), invoice.totalAmount());
		assertEquals(new Euro(0), invoice.vat());
		assertEquals(new Euro(0), invoice.toPay());
	}

	@Test
	public void oneEntryInvoice() {
		List<Entry<Day>> entries = new ArrayList<Entry<Day>>();
		entries.add(new SimpleInvoiceEntry(Unit.days(1), new Euro(100), new __TestObjectMother().me(), new __TestObjectMother().myCompany(), Day.january(31, 2012)));
		Invoice invoice = new Invoice(new __TestObjectMother().myCompany(), Day.january(31, 2012), entries);
		assertClientEquals(new __TestObjectMother().myCompany(), invoice.client());
		assertEquals(Day.january(31, 2012), invoice.issueDate());
		assertEquals(new Euro(100), invoice.totalAmount());
		assertEquals(new Euro(21), invoice.vat());
		assertEquals(new Euro(121), invoice.toPay());
	}

	@Test
	public void twoEntryInvoice() {
		List<Entry<Day>> entries = new ArrayList<Entry<Day>>();
		entries.add(new SimpleInvoiceEntry(Unit.days(1), new Euro(60), new __TestObjectMother().me(), new __TestObjectMother().myCompany(), Day.january(31, 2012)));
		entries.add(new SimpleInvoiceEntry(Unit.days(1), new Euro(40), new __TestObjectMother().me(), new __TestObjectMother().myCompany(), Day.january(31, 2012)));
		Invoice invoice = new Invoice(new __TestObjectMother().myCompany(), Day.january(31, 2012), entries);
		assertClientEquals(new __TestObjectMother().myCompany(), invoice.client());
		assertEquals(Day.january(31, 2012), invoice.issueDate());
		assertEquals(new Euro(100), invoice.totalAmount());
		assertEquals(new Euro(21), invoice.vat());
		assertEquals(new Euro(121), invoice.toPay());
	}
}

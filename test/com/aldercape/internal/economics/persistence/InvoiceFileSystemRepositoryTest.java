package com.aldercape.internal.economics.persistence;

import static com.aldercape.internal.economics.model.CustomModelAsserts.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Invoice;
import com.aldercape.internal.economics.model.InvoiceBuilder;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.SimpleInvoiceEntry;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class InvoiceFileSystemRepositoryTest {

	@Rule
	public TemporaryFolder baseFolder = new TemporaryFolder();
	private File invoiceFile;
	private Invoice firstInvoice;
	private InvoiceFileSystemRepository repository;
	private String entryJson;
	private __FileSystemRepositories repositories;

	@Before
	public void setUp() throws IOException {
		invoiceFile = baseFolder.newFile("invoice.json");
		repositories = new __FileSystemRepositories(baseFolder.getRoot());
		repository = new InvoiceFileSystemRepository(invoiceFile, repositories.invoiceEntryRepository(), repositories.timeEntryRepository());

		__TestObjectMother objectMother = new __TestObjectMother();
		repositories.collaboratorRepository().add(objectMother.me());
		repositories.clientRepository().add(objectMother.otherCompany());
		SimpleInvoiceEntry validEntry = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(100)), objectMother.me(), objectMother.otherCompany(), Day.january(31, 2012));
		repositories.timeEntryRepository().add(validEntry.getAllEntries().iterator().next());
		repositories.invoiceEntryRepository().add(validEntry);
		firstInvoice = new InvoiceBuilder(objectMother.myCompany()).daysToPay(30).forClient(objectMother.otherCompany()).issued(Day.january(31, 2012)).addEntry(validEntry).create();
		entryJson = "{\"company\":{\"name\":\"My Company\",\"vatNumber\":\"0123456789\",\"address\":{\"streetName\":\"Sesame Street\",\"streetNumber\":\"1\",\"zipcode\":\"12345\",\"city\":\"My City\"},\"contactPerson\":\"My contact\"},\"client\":{\"name\":\"Other Company\",\"vatNumber\":\"9876543210\",\"address\":{\"streetName\":\"Other street\",\"streetNumber\":\"5\",\"zipcode\":\"54321\",\"city\":\"Other City\"},\"contactPerson\":\"Other contact\"},\"issueDate\":{\"day\":31,\"month\":{\"month\":\"January\",\"year\":2012}},\"dueDate\":{\"day\":1,\"month\":{\"month\":\"Mars\",\"year\":2012}},\"entries\":[{\"timeEntries\":[1]}]}";
	}

	@Test
	public void emptyFileShouldHaveNoInvoices() {
		assertTrue(repository.getAll().isEmpty());
	}

	@Test
	public void shouldSaveOneEntryToFileSystemOnAddInJsonFormat() throws Exception {
		assertEquals(0, invoiceFile.length());
		repository.add(firstInvoice);
		assertTrue(invoiceFile.length() > 0);
		assertEquals("{\"1\":" + entryJson + "}", getContent(invoiceFile));
	}

	@Test
	public void shouldHaveOneClientIfFileHaveOneClientOnInstanciation() throws Exception {
		createFileWithContent("{\"1\":" + entryJson + "}");
		assertFalse(invoiceFile.length() == 0);
		repository = new InvoiceFileSystemRepository(invoiceFile, repositories.invoiceEntryRepository(), repositories.timeEntryRepository());
		List<Invoice> all = repository.getAll();
		assertEquals(1, all.size());
		assertInvoiceEquals(firstInvoice, all.get(0));
	}

	// @Test
	// public void shouldHaveTwoClientIfFileHaveTwoClientOnInstanciation()
	// throws Exception {
	// createFileWithContent("{\"1\":" + entryJson + ", \"2\":" + otherEntryJson
	// + "}");
	// assertFalse(invoiceFile.length() == 0);
	// repository = new InvoiceFileSystemRepository(invoiceFile);
	// List<Invoice> all = repository.getAll();
	// assertEquals(2, all.size());
	// assertInvoiceEquals(firstInvoiceEntry, all.get(0));
	// assertInvoiceEquals(secondInvoiceEntry, all.get(1));
	// }
	//
	private String getContent(File newFile) throws IOException {
		StringBuilder result = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(newFile));
		int c;
		while ((c = reader.read()) != -1) {
			result.append((char) c);
		}
		return result.toString();
	}

	private void createFileWithContent(String content) throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(invoiceFile));
			writer.write(content);
			writer.flush();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}

}

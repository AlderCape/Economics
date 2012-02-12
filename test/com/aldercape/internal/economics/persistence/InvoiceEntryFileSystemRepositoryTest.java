package com.aldercape.internal.economics.persistence;

import static com.aldercape.internal.economics.model.CustomModelAsserts.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.InvoiceEntryBuilder;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.TimeEntryRepository;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class InvoiceEntryFileSystemRepositoryTest {

	@Rule
	public TemporaryFolder baseFolder = new TemporaryFolder();
	private File invoiceEntryFile;
	private InvoiceEntryFileSystemRepository repository;
	private String entryJson;
	private TimeEntry entry;
	private TimeEntry otherEntry;
	private String otherEntryJson;
	private CollaboratorRepository collaboratorRepository;
	private ClientRepository clientRepository;
	private TimeEntryRepository timeEntryRepository;
	private TimeEntry thirdEntry;
	private InvoiceEntry firstInvoiceEntry;
	private InvoiceEntry secondInvoiceEntry;

	@Before
	public void setUp() throws IOException {
		invoiceEntryFile = baseFolder.newFile("TestFile");
		__FileSystemRepositories repositories = new __FileSystemRepositories(baseFolder.getRoot());
		__TestObjectMother objectMother = new __TestObjectMother();

		collaboratorRepository = repositories.collaboratorRepository();
		clientRepository = repositories.clientRepository();
		timeEntryRepository = repositories.timeEntryRepository();

		collaboratorRepository.add(objectMother.me());
		collaboratorRepository.add(objectMother.other());
		clientRepository.add(objectMother.myCompany());
		clientRepository.add(objectMother.otherCompany());

		repository = new InvoiceEntryFileSystemRepository(invoiceEntryFile, timeEntryRepository);
		entry = new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), objectMother.me(), objectMother.otherCompany(), Day.january(2, 2012));
		timeEntryRepository.add(entry);
		otherEntry = new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), objectMother.other(), objectMother.myCompany(), Day.january(2, 2012));
		timeEntryRepository.add(otherEntry);
		thirdEntry = new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), objectMother.other(), objectMother.myCompany(), Day.january(3, 2012));
		timeEntryRepository.add(thirdEntry);

		entryJson = "{\"timeEntries\":[1]}";
		otherEntryJson = "{\"timeEntries\":[2,3]}";
		firstInvoiceEntry = new InvoiceEntryBuilder(Collections.singleton(entry)).createInvoiceEntry().iterator().next();

		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(otherEntry);
		entries.add(thirdEntry);
		secondInvoiceEntry = new InvoiceEntryBuilder(entries).createInvoiceEntry().iterator().next();
	}

	@Test
	public void emptyFileShouldHaveNoClients() {
		assertTrue(repository.getAll().isEmpty());
	}

	@Test
	public void shouldSaveOneEntryToFileSystemOnAddInJsonFormat() throws Exception {
		assertEquals(0, invoiceEntryFile.length());
		repository.add(firstInvoiceEntry);
		assertTrue(invoiceEntryFile.length() > 0);
		assertEquals("{\"1\":" + entryJson + "}", getContent(invoiceEntryFile));
	}

	@Test
	public void shouldSaveTwoEntriesToFileSystemOnAddInJsonFormat() throws Exception {
		assertEquals(0, invoiceEntryFile.length());
		repository.add(firstInvoiceEntry);
		repository.add(secondInvoiceEntry);
		assertTrue(invoiceEntryFile.length() > 0);
		assertEquals("{\"1\":" + entryJson + ",\"2\":" + otherEntryJson + "}", getContent(invoiceEntryFile));
	}

	@Test
	public void shouldHaveOneClientIfFileHaveOneClientOnInstanciation() throws Exception {
		createFileWithContent("{\"1\":" + entryJson + "}");
		assertFalse(invoiceEntryFile.length() == 0);
		repository = new InvoiceEntryFileSystemRepository(invoiceEntryFile, timeEntryRepository);
		List<InvoiceEntry> all = repository.getAll();
		assertEquals(1, all.size());
		assertInvoiceEntryEquals(firstInvoiceEntry, all.get(0));
	}

	@Test
	public void shouldHaveTwoClientIfFileHaveTwoClientOnInstanciation() throws Exception {
		createFileWithContent("{\"1\":" + entryJson + ", \"2\":" + otherEntryJson + "}");
		assertFalse(invoiceEntryFile.length() == 0);
		repository = new InvoiceEntryFileSystemRepository(invoiceEntryFile, timeEntryRepository);
		List<InvoiceEntry> all = repository.getAll();
		assertEquals(2, all.size());
		assertInvoiceEntryEquals(firstInvoiceEntry, all.get(0));
		assertInvoiceEntryEquals(secondInvoiceEntry, all.get(1));
	}

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
			writer = new BufferedWriter(new FileWriter(invoiceEntryFile));
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

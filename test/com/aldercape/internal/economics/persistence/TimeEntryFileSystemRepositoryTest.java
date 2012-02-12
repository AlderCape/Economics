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
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class TimeEntryFileSystemRepositoryTest {

	@Rule
	public TemporaryFolder baseFolder = new TemporaryFolder();
	private File timeEntryFile;
	TimeEntryFileSystemRepository repository;
	private String entryJson;
	private TimeEntry entry;
	private TimeEntry otherEntry;
	private String otherEntryJson;
	private CollaboratorFileSystemRepository collaboratorRepository;
	private ClientFileSystemRepository clientRepository;

	@Before
	public void setUp() throws IOException {
		timeEntryFile = baseFolder.newFile("TestFile");
		File collaboratorFile = baseFolder.newFile("collaborators.json");
		File clientsFile = baseFolder.newFile("clients.json");
		collaboratorRepository = new CollaboratorFileSystemRepository(collaboratorFile);
		__TestObjectMother objectMother = new __TestObjectMother();
		collaboratorRepository.add(objectMother.me());
		collaboratorRepository.add(objectMother.other());
		clientRepository = new ClientFileSystemRepository(clientsFile);
		clientRepository.add(objectMother.myCompany());
		clientRepository.add(objectMother.otherCompany());
		repository = new TimeEntryFileSystemRepository(timeEntryFile, collaboratorRepository, clientRepository);
		entry = new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), objectMother.me(), objectMother.otherCompany(), Day.january(2, 2012));
		entryJson = "{\"collaboratorId\":1,\"clientId\":2,\"rate\":{\"amount\":{\"amount\":200}},\"unit\":{\"amount\":1,\"unit\":\"DAY\"},\"day\":{\"day\":2,\"month\":{\"month\":\"January\",\"year\":2012}}}";
		otherEntry = new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), objectMother.other(), objectMother.myCompany(), Day.january(2, 2012));
		otherEntryJson = "{\"collaboratorId\":2,\"clientId\":1,\"rate\":{\"amount\":{\"amount\":200}},\"unit\":{\"amount\":1,\"unit\":\"DAY\"},\"day\":{\"day\":2,\"month\":{\"month\":\"January\",\"year\":2012}}}";
	}

	@Test
	public void emptyFileShouldHaveNoClients() {
		assertTrue(repository.getAll().isEmpty());
	}

	@Test
	public void shouldSaveOneEntryToFileSystemOnAddInJsonFormat() throws Exception {
		assertEquals(0, timeEntryFile.length());
		repository.add(entry);
		assertTrue(timeEntryFile.length() > 0);
		assertEquals("{\"1\":" + entryJson + "}", getContent(timeEntryFile));
	}

	@Test
	public void shouldSaveTwoEntriesToFileSystemOnAddInJsonFormat() throws Exception {
		assertEquals(0, timeEntryFile.length());
		repository.add(entry);
		repository.add(otherEntry);
		assertTrue(timeEntryFile.length() > 0);
		assertEquals("{\"1\":" + entryJson + ",\"2\":" + otherEntryJson + "}", getContent(timeEntryFile));
	}

	@Test
	public void shouldHaveOneClientIfFileHaveOneClientOnInstanciation() throws Exception {
		createFileWithContent("{\"1\":" + entryJson + "}");
		assertFalse(timeEntryFile.length() == 0);
		repository = new TimeEntryFileSystemRepository(timeEntryFile, collaboratorRepository, clientRepository);
		List<TimeEntry> all = repository.getAll();
		assertEquals(1, all.size());
		assertTimeEntryEquals(entry, all.get(0));
	}

	@Test
	public void shouldHaveTwoClientIfFileHaveTwoClientOnInstanciation() throws Exception {
		createFileWithContent("{\"1\":" + entryJson + ", \"2\":" + otherEntryJson + "}");
		assertFalse(timeEntryFile.length() == 0);
		repository = new TimeEntryFileSystemRepository(timeEntryFile, collaboratorRepository, clientRepository);
		List<TimeEntry> all = repository.getAll();
		assertEquals(2, all.size());
		assertTimeEntryEquals(entry, all.get(0));
		assertTimeEntryEquals(otherEntry, all.get(1));
	}

	@Test
	public void shouldBePossibleToGetTheIdFromAnEntry() throws Exception {
		createFileWithContent("{\"1\":" + entryJson + ", \"2\":" + otherEntryJson + "}");
		assertFalse(timeEntryFile.length() == 0);
		repository = new TimeEntryFileSystemRepository(timeEntryFile, collaboratorRepository, clientRepository);
		Set<Long> all = repository.getIdsFor(Collections.singleton(entry));
		assertEquals(1, all.size());
		assertEquals(1L, (long) all.iterator().next());

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
			writer = new BufferedWriter(new FileWriter(timeEntryFile));
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

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

import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.NoMatchingRecordException;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class CollaboratorFileSystemRepositoryTest {

	@Rule
	public TemporaryFolder baseFolder = new TemporaryFolder();
	private File newFile;
	private CollaboratorFileSystemRepository repository;
	private String meJson;
	private String otherJson;

	@Before
	public void setUp() throws IOException {
		newFile = baseFolder.newFile("TestFile");
		repository = new CollaboratorFileSystemRepository(newFile);
		meJson = "{\"firstname\":\"Me\",\"lastname\":\"Surname\",\"email\":\"me@mycompany.com\"}";
		otherJson = "{\"firstname\":\"Other\",\"lastname\":\"Something\",\"email\":\"other@othercompany.com\"}";
	}

	@Test
	public void emptyFileShouldHaveNoClients() {
		assertTrue(repository.getAll().isEmpty());
	}

	@Test(expected = NoMatchingRecordException.class)
	public void shouldThrowExceptionIfCollaboratorIsNotPresentOnGet() {
		repository.findByEmail("No such collaborator");
	}

	@Test
	public void shouldSaveOneClientToFileSystemOnAddInJsonFormat() throws Exception {
		assertEquals(0, newFile.length());
		repository.add(new __TestObjectMother().me());
		assertTrue(newFile.length() > 0);
		assertEquals("{\"1\":" + meJson + "}", getContent(newFile));
	}

	@Test
	public void shouldSaveTwoClientsToFileSystemOnAddInJsonFormat() throws Exception {
		assertEquals(0, newFile.length());
		repository.add(new __TestObjectMother().me());
		repository.add(new __TestObjectMother().other());
		assertTrue(newFile.length() > 0);
		assertEquals("{\"1\":" + meJson + ",\"2\":" + otherJson + "}", getContent(newFile));
	}

	@Test
	public void shouldHaveOneClientIfFileHaveOneClientOnInstanciation() throws Exception {
		createFileWithContent("{\"1\":" + meJson + "}");
		assertFalse(newFile.length() == 0);
		repository = new CollaboratorFileSystemRepository(newFile);
		List<Collaborator> all = repository.getAll();
		assertEquals(1, all.size());
		assertCollaboratorEquals(new __TestObjectMother().me(), all.get(0));
	}

	@Test
	public void shouldHaveTwoClientIfFileHaveTwoClientOnInstanciation() throws Exception {
		createFileWithContent("{\"1\":" + meJson + ", \"2\":" + otherJson + "}");
		assertFalse(newFile.length() == 0);
		repository = new CollaboratorFileSystemRepository(newFile);
		List<Collaborator> all = repository.getAll();
		assertEquals(2, all.size());
		assertCollaboratorEquals(new __TestObjectMother().me(), all.get(0));
	}

	@Test
	public void testFindByEmail() {
		repository.add(new __TestObjectMother().me());
		Collaborator byEmail = repository.findByEmail(new __TestObjectMother().me().email());
		assertCollaboratorEquals(new __TestObjectMother().me(), byEmail);
	}

	private void createFileWithContent(String content) throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(newFile));
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

	private String getContent(File newFile) throws IOException {
		StringBuilder result = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(newFile));
		int c;
		while ((c = reader.read()) != -1) {
			result.append((char) c);
		}
		return result.toString();
	}
}

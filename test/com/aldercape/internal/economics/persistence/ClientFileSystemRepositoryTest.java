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

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.NoMatchingRecordException;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class ClientFileSystemRepositoryTest {

	@Rule
	public TemporaryFolder baseFolder = new TemporaryFolder();
	private File newFile;
	private ClientFileSystemRepository repository;
	private String myCompanyJson;
	private String otherCompanyJSon;
	private __FileSystemRepositories repositories;

	@Before
	public void setUp() throws IOException {
		repositories = new __FileSystemRepositories(baseFolder.getRoot());
		newFile = baseFolder.newFile("TestFile");
		repository = new ClientFileSystemRepository(newFile);
		myCompanyJson = "{\"name\":\"My Company\",\"vatNumber\":\"0123456789\",\"address\":{\"streetName\":\"Sesame Street\",\"streetNumber\":\"1\",\"zipcode\":\"12345\",\"city\":\"My City\"},\"contactPerson\":\"My contact\"}";
		otherCompanyJSon = "{\"name\":\"Other Company\",\"vatNumber\":\"9876543210\",\"address\":{\"streetName\":\"Other street\",\"streetNumber\":\"5\",\"zipcode\":\"54321\",\"city\":\"Other City\"},\"contactPerson\":\"Other contact\"}";
	}

	@Test
	public void emptyFileShouldHaveNoClients() {
		assertTrue(repository.getAll().isEmpty());
	}

	@Test(expected = NoMatchingRecordException.class)
	public void shouldThrowExceptionIfClientIsNotPresentOnGet() {
		repository.getByName("No such client");
	}

	@Test
	public void shouldSaveOneClientToFileSystemOnAddInJsonFormat() throws Exception {
		assertEquals(0, newFile.length());
		repository.add(new __TestObjectMother().myCompany());
		assertTrue(newFile.length() > 0);
		assertEquals("{\"1\":" + myCompanyJson + "}", getContent(newFile));
	}

	@Test
	public void shouldSaveTwoClientsToFileSystemOnAddInJsonFormat() throws Exception {
		assertEquals(0, newFile.length());
		repository.add(new __TestObjectMother().myCompany());
		repository.add(new __TestObjectMother().otherCompany());
		assertTrue(newFile.length() > 0);
		assertEquals("{\"1\":" + myCompanyJson + ",\"2\":" + otherCompanyJSon + "}", getContent(newFile));
	}

	@Test
	public void shouldHaveOneClientIfFileHaveOneClientOnInstanciation() throws Exception {
		createFileWithContent("{\"1\":" + myCompanyJson + "}");
		assertFalse(newFile.length() == 0);
		repository = new ClientFileSystemRepository(newFile);
		List<Client> all = repository.getAll();
		assertEquals(1, all.size());
		assertClientEquals(new __TestObjectMother().myCompany(), all.get(0));
	}

	@Test
	public void shouldHaveTwoClientIfFileHaveTwoClientOnInstanciation() throws Exception {
		createFileWithContent("{\"1\":" + myCompanyJson + ", \"2\":" + otherCompanyJSon + "}");
		assertFalse(newFile.length() == 0);
		repository = new ClientFileSystemRepository(newFile);
		List<Client> all = repository.getAll();
		assertEquals(2, all.size());
		assertClientEquals(new __TestObjectMother().myCompany(), all.get(0));
	}

	@Test
	public void testGetByName() {
		repository.add(new __TestObjectMother().myCompany());
		Client byName = repository.getByName(new __TestObjectMother().myCompany().name());
		assertClientEquals(new __TestObjectMother().myCompany(), byName);
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

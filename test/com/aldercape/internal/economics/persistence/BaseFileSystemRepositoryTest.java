package com.aldercape.internal.economics.persistence;

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

import com.aldercape.internal.economics.model.BaseRepository;

public abstract class BaseFileSystemRepositoryTest<T> {

	@Rule
	public TemporaryFolder baseFolder = new TemporaryFolder();
	private File newFile;

	@Before
	public void baseSetUp() throws IOException {
		newFile = baseFolder.newFile("TestFileBaseClass");
		createNewRepository(newFile);
	}

	@Test
	public void emptyFileShouldHaveNoEntries() {
		assertTrue(getRepository().getAll().isEmpty());
	}

	@Test
	public void shouldSaveOneClientToFileSystemOnAddInJsonFormat() throws Exception {
		assertEquals(0, newFile.length());
		getRepository().add(getFirstEntry());
		assertTrue(newFile.length() > 0);
		assertEquals("{\"1\":" + getFirstEntryJson() + "}", getContent(newFile));
	}

	@Test
	public void shouldSaveTwoClientsToFileSystemOnAddInJsonFormat() throws Exception {
		assertEquals(0, newFile.length());
		getRepository().add(getFirstEntry());
		getRepository().add(getSecondEntry());
		assertTrue(newFile.length() > 0);
		assertEquals("{\"1\":" + getFirstEntryJson() + ",\"2\":" + getSecondEntryJson() + "}", getContent(newFile));
	}

	@Test
	public void shouldHaveOneEntryIfFileHaveOneEntryOnInstanciation() throws Exception {
		createFileWithContent("{\"1\":" + getFirstEntryJson() + "}");
		assertFalse(newFile.length() == 0);
		createNewRepository(newFile);
		List<T> all = getRepository().getAll();
		assertEquals(1, all.size());
		assertEntryEquals(getFirstEntry(), all.get(0));
	}

	@Test
	public void shouldHaveTwoClientIfFileHaveTwoClientOnInstanciation() throws Exception {
		createFileWithContent("{\"1\":" + getFirstEntryJson() + ", \"2\":" + getSecondEntryJson() + "}");
		assertFalse(newFile.length() == 0);
		createNewRepository(newFile);
		List<T> all = getRepository().getAll();
		assertEquals(2, all.size());
		assertEntryEquals(getFirstEntry(), all.get(0));
	}

	protected abstract String getFirstEntryJson();

	protected abstract String getSecondEntryJson();

	protected abstract T getFirstEntry();

	protected abstract BaseRepository<T> getRepository();

	protected abstract void assertEntryEquals(T expected, T actual);

	protected abstract void createNewRepository(File newFile);

	protected abstract T getSecondEntry();

	protected void createFileWithContent(String content) throws IOException {
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

	protected String getContent(File newFile) throws IOException {
		StringBuilder result = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(newFile));
		int c;
		while ((c = reader.read()) != -1) {
			result.append((char) c);
		}
		return result.toString();
	}

}

package com.aldercape.internal.economics.persistence;

import static com.aldercape.internal.economics.model.CustomModelAsserts.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.BaseRepository;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class TimeEntryFileSystemRepositoryTest extends BaseFileSystemRepositoryTest<TimeEntry> {

	TimeEntryFileSystemRepository repository;
	private TimeEntry entry;
	private TimeEntry otherEntry;
	private RepositoryRegistry repositoryRegistry;
	private __FileSystemRepositories repositories;

	@Before
	public void setUp() throws IOException {
		__TestObjectMother objectMother = new __TestObjectMother();
		repositories.collaboratorRepository().add(objectMother.me());
		repositories.collaboratorRepository().add(objectMother.other());
		repositories.clientRepository().add(objectMother.myCompany());
		repositories.clientRepository().add(objectMother.otherCompany());

		entry = new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), objectMother.me(), objectMother.otherCompany(), Day.january(2, 2012));
		otherEntry = new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), objectMother.other(), objectMother.myCompany(), Day.january(2, 2012));
	}

	@Test
	public void shouldBePossibleToGetTheIdFromAnEntry() throws Exception {
		createFileWithContent("{\"1\":" + getFirstEntryJson() + ", \"2\":" + getSecondEntryJson() + "}");
		assertFalse(newFile.length() == 0);
		createNewRepository(newFile);
		Set<Long> all = repository.getIdsFor(Collections.singleton(entry));
		assertEquals(1, all.size());
		assertEquals(1L, (long) all.iterator().next());

	}

	@Override
	protected String getFirstEntryJson() {
		return "{\"collaboratorId\":1,\"clientId\":2,\"rate\":{\"amount\":{\"amount\":200}},\"unit\":{\"amount\":1,\"unit\":\"DAY\"},\"day\":{\"day\":2,\"month\":{\"month\":\"January\",\"year\":2012}}}";
	}

	@Override
	protected String getSecondEntryJson() {
		return "{\"collaboratorId\":2,\"clientId\":1,\"rate\":{\"amount\":{\"amount\":200}},\"unit\":{\"amount\":1,\"unit\":\"DAY\"},\"day\":{\"day\":2,\"month\":{\"month\":\"January\",\"year\":2012}}}";
	}

	@Override
	protected TimeEntry getFirstEntry() {
		return entry;
	}

	@Override
	protected TimeEntry getSecondEntry() {
		return otherEntry;
	}

	@Override
	protected BaseRepository<TimeEntry> getRepository() {
		return repository;
	}

	@Override
	protected void assertEntryEquals(TimeEntry expected, TimeEntry actual) {
		assertTimeEntryEquals(expected, actual);
	}

	@Override
	protected void createNewRepository(File newFile) {
		repositories = new __FileSystemRepositories(newFile.getParentFile());

		repositoryRegistry = new RepositoryRegistry();
		repositoryRegistry.setRepository(ClientRepository.class, repositories.clientRepository());
		repositoryRegistry.setRepository(CollaboratorRepository.class, repositories.collaboratorRepository());

		repository = new TimeEntryFileSystemRepository(newFile, repositoryRegistry);
	}

}

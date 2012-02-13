package com.aldercape.internal.economics.persistence;

import static com.aldercape.internal.economics.model.CustomModelAsserts.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;

import com.aldercape.internal.economics.model.BaseRepository;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.InvoiceEntryBuilder;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.TimeEntryRepository;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class InvoiceEntryFileSystemRepositoryTest extends BaseFileSystemRepositoryTest<InvoiceEntry> {

	private InvoiceEntryFileSystemRepository repository;
	private InvoiceEntry firstInvoiceEntry;
	private InvoiceEntry secondInvoiceEntry;
	private RepositoryRegistry repositoryRegistry;
	private __FileSystemRepositories repositories;

	@Before
	public void setUp() throws IOException {
		__TestObjectMother objectMother = new __TestObjectMother();

		repositories.collaboratorRepository().add(objectMother.me());
		repositories.collaboratorRepository().add(objectMother.other());
		repositories.clientRepository().add(objectMother.myCompany());
		repositories.clientRepository().add(objectMother.otherCompany());

		TimeEntry entry = new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), objectMother.me(), objectMother.otherCompany(), Day.january(2, 2012));
		repositories.timeEntryRepository().add(entry);
		TimeEntry otherEntry = new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), objectMother.other(), objectMother.myCompany(), Day.january(2, 2012));
		repositories.timeEntryRepository().add(otherEntry);
		TimeEntry thirdEntry = new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), objectMother.other(), objectMother.myCompany(), Day.january(3, 2012));
		repositories.timeEntryRepository().add(thirdEntry);

		firstInvoiceEntry = new InvoiceEntryBuilder(Collections.singleton(entry)).createInvoiceEntry().iterator().next();

		Set<TimeEntry> entries = new HashSet<TimeEntry>();
		entries.add(otherEntry);
		entries.add(thirdEntry);
		secondInvoiceEntry = new InvoiceEntryBuilder(entries).createInvoiceEntry().iterator().next();
	}

	@Override
	protected String getFirstEntryJson() {
		return "{\"timeEntries\":[1]}";
	}

	@Override
	protected String getSecondEntryJson() {
		return "{\"timeEntries\":[2,3]}";
	}

	@Override
	protected InvoiceEntry getFirstEntry() {
		return firstInvoiceEntry;
	}

	@Override
	protected InvoiceEntry getSecondEntry() {
		return secondInvoiceEntry;
	}

	@Override
	protected BaseRepository<InvoiceEntry> getRepository() {
		return repository;
	}

	@Override
	protected void assertEntryEquals(InvoiceEntry expected, InvoiceEntry actual) {
		assertInvoiceEntryEquals(expected, actual);
	}

	@Override
	protected void createNewRepository(File newFile) {
		repositories = new __FileSystemRepositories(newFile.getParentFile());

		repositoryRegistry = new RepositoryRegistry();
		repositoryRegistry.setRepository(TimeEntryRepository.class, repositories.timeEntryRepository());

		repository = new InvoiceEntryFileSystemRepository(newFile, repositoryRegistry);
	}

}

package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.io.IOException;

import org.junit.Before;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Invoice;
import com.aldercape.internal.economics.model.InvoiceBuilder;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.SimpleInvoiceEntry;
import com.aldercape.internal.economics.model.TimeEntryRepository;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class InvoiceFileSystemRepositoryTest extends BaseFileSystemRepositoryTest<Invoice> {

	private Invoice firstInvoice;
	private InvoiceFileSystemRepository repository;
	private __FileSystemRepositories repositories;
	private RepositoryRegistry repositoryRegistry;
	private Invoice secondInvoice;

	@Before
	public void setUp() throws IOException {
		__TestObjectMother objectMother = new __TestObjectMother();
		repositories.collaboratorRepository().add(objectMother.me());
		repositories.clientRepository().add(objectMother.otherCompany());

		SimpleInvoiceEntry validEntry = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(100)), objectMother.me(), objectMother.otherCompany(), Day.january(31, 2012));
		repositories.timeEntryRepository().add(validEntry.getAllEntries().iterator().next());
		repositories.invoiceEntryRepository().add(validEntry);
		firstInvoice = new InvoiceBuilder(objectMother.myCompany()).daysToPay(30).forClient(objectMother.otherCompany()).issued(Day.january(31, 2012)).addEntry(validEntry).create();

		SimpleInvoiceEntry secondEntry = new SimpleInvoiceEntry(Unit.days(1), Rate.daily(new Euro(100)), objectMother.me(), objectMother.otherCompany(), Day.february(1, 2012));
		repositories.timeEntryRepository().add(secondEntry.getAllEntries().iterator().next());
		repositories.invoiceEntryRepository().add(secondEntry);
		secondInvoice = new InvoiceBuilder(objectMother.myCompany()).daysToPay(30).forClient(objectMother.otherCompany()).issued(Day.january(31, 2012)).addEntry(secondEntry).create();
	}

	@Override
	protected String getFirstEntryJson() {
		return "{\"company\":{\"name\":\"My Company\",\"vatNumber\":\"0123456789\",\"address\":{\"streetName\":\"Sesame Street\",\"streetNumber\":\"1\",\"zipcode\":\"12345\",\"city\":\"My City\"},\"contactPerson\":\"My contact\"},\"client\":{\"name\":\"Other Company\",\"vatNumber\":\"9876543210\",\"address\":{\"streetName\":\"Other street\",\"streetNumber\":\"5\",\"zipcode\":\"54321\",\"city\":\"Other City\"},\"contactPerson\":\"Other contact\"},\"issueDate\":{\"day\":31,\"month\":{\"month\":\"January\",\"year\":2012}},\"dueDate\":{\"day\":1,\"month\":{\"month\":\"Mars\",\"year\":2012}},\"entries\":[{\"timeEntries\":[1]}]}";
	}

	@Override
	protected String getSecondEntryJson() {
		return "{\"company\":{\"name\":\"My Company\",\"vatNumber\":\"0123456789\",\"address\":{\"streetName\":\"Sesame Street\",\"streetNumber\":\"1\",\"zipcode\":\"12345\",\"city\":\"My City\"},\"contactPerson\":\"My contact\"},\"client\":{\"name\":\"Other Company\",\"vatNumber\":\"9876543210\",\"address\":{\"streetName\":\"Other street\",\"streetNumber\":\"5\",\"zipcode\":\"54321\",\"city\":\"Other City\"},\"contactPerson\":\"Other contact\"},\"issueDate\":{\"day\":31,\"month\":{\"month\":\"January\",\"year\":2012}},\"dueDate\":{\"day\":1,\"month\":{\"month\":\"Mars\",\"year\":2012}},\"entries\":[{\"timeEntries\":[2]}]}";
	}

	@Override
	protected Invoice getFirstEntry() {
		return firstInvoice;
	}

	@Override
	protected Invoice getSecondEntry() {
		return secondInvoice;
	}

	@Override
	protected InvoiceFileSystemRepository getRepository() {
		return repository;
	}

	@Override
	protected void assertEntryEquals(Invoice expected, Invoice actual) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createNewRepository(File newFile) {
		repositories = new __FileSystemRepositories(newFile.getParentFile());
		repositoryRegistry = new RepositoryRegistry();
		repositoryRegistry.setRepository(TimeEntryRepository.class, repositories.timeEntryRepository());

		repository = new InvoiceFileSystemRepository(newFile, repositoryRegistry);

	}

}

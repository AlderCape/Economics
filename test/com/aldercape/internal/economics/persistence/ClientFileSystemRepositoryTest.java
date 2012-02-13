package com.aldercape.internal.economics.persistence;

import static com.aldercape.internal.economics.model.CustomModelAsserts.*;

import java.io.File;

import org.junit.Test;

import com.aldercape.internal.economics.model.BaseRepository;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.NoMatchingRecordException;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class ClientFileSystemRepositoryTest extends BaseFileSystemRepositoryTest<Client> {

	private ClientFileSystemRepository repository;

	@Test(expected = NoMatchingRecordException.class)
	public void shouldThrowExceptionIfClientIsNotPresentOnGet() {
		repository.getByName("No such client");
	}

	@Test
	public void testGetByName() {
		getRepository().add(getFirstEntry());
		Client byName = repository.getByName(getFirstEntry().name());
		assertClientEquals(getFirstEntry(), byName);
	}

	@Override
	protected Client getFirstEntry() {
		return new __TestObjectMother().myCompany();
	}

	@Override
	protected Client getSecondEntry() {
		return new __TestObjectMother().otherCompany();
	}

	@Override
	protected void createNewRepository(File newFile) {
		repository = new ClientFileSystemRepository(newFile);
	}

	@Override
	protected String getFirstEntryJson() {
		return "{\"name\":\"My Company\",\"vatNumber\":\"0123456789\",\"address\":{\"streetName\":\"Sesame Street\",\"streetNumber\":\"1\",\"zipcode\":\"12345\",\"city\":\"My City\"},\"contactPerson\":\"My contact\"}";
	}

	@Override
	protected String getSecondEntryJson() {
		return "{\"name\":\"Other Company\",\"vatNumber\":\"9876543210\",\"address\":{\"streetName\":\"Other street\",\"streetNumber\":\"5\",\"zipcode\":\"54321\",\"city\":\"Other City\"},\"contactPerson\":\"Other contact\"}";
	}

	@Override
	protected BaseRepository<Client> getRepository() {
		return repository;
	}

	@Override
	protected void assertEntryEquals(Client expected, Client actual) {
		assertClientEquals(expected, actual);
	}
}

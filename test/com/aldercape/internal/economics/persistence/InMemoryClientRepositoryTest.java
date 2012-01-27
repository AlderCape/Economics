package com.aldercape.internal.economics.persistence;

import static com.aldercape.internal.economics.model.CustomModelAsserts.assertClientEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aldercape.internal.economics.model.BaseRepository.Listener;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.NoMatchingRecordException;

public class InMemoryClientRepositoryTest extends __InMemoryRepositoryTest<Client> implements Listener {

	private boolean changedCalled;

	@Override
	protected InMemoryClientRepository getRepository() {
		return new InMemoryClientRepository();
	}

	@Override
	protected Client getSecondEntry() {
		return objectMother.myCompany();
	}

	@Override
	protected Client getFirstEntry() {
		return objectMother.otherCompany();
	}

	@Test
	public void shouldNotifyListenersWhenClientIsAdded() {
		InMemoryClientRepository repository = getRepository();
		repository.addListener(this);
		assertFalse(changedCalled);
		repository.add(objectMother.myCompany());
		assertTrue(changedCalled);
	}

	@Test
	public void shouldGetCorrectClientOnName() {
		InMemoryClientRepository repository = getRepository();
		repository.add(objectMother.myCompany());
		repository.add(objectMother.otherCompany());

		Client foundClient = repository.getByName(objectMother.myCompany().name());
		assertClientEquals(objectMother.myCompany(), foundClient);
	}

	@Test(expected = NoMatchingRecordException.class)
	public void shouldThrowExceptionIfNotFound() {
		InMemoryClientRepository repository = getRepository();
		repository.add(objectMother.myCompany());
		repository.add(objectMother.otherCompany());

		repository.getByName("");
	}

	@Override
	public void changed() {
		changedCalled = true;
	}

}

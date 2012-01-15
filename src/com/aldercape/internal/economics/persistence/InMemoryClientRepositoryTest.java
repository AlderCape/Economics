package com.aldercape.internal.economics.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.BaseRepository.Listener;

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

	@Override
	public void changed() {
		changedCalled = true;
	}

}

package com.aldercape.internal.economics.ui.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.CustomModelAsserts;
import com.aldercape.internal.economics.persistence.InMemoryClientRepository;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class ClientRepositoryComboBoxModelTest {

	public static final class ListDataListenerSpy implements ListDataListener {
		public boolean intervalRemovedCalled;
		public boolean intervalAddedCalled;
		public boolean contentsChangedCalled;

		@Override
		public void intervalRemoved(ListDataEvent e) {
			intervalRemovedCalled = true;
		}

		@Override
		public void intervalAdded(ListDataEvent e) {
			intervalAddedCalled = true;
		}

		@Override
		public void contentsChanged(ListDataEvent e) {
			contentsChangedCalled = true;
		}
	}

	public class InMemoryClientRepositorySpy extends InMemoryClientRepository {

		public int getAllCalledCount;

		@Override
		public List<Client> getAll() {
			getAllCalledCount++;
			return super.getAll();
		}
	}

	private __TestObjectMother objectMother;
	private ClientRepositoryComboBoxModel model;
	private InMemoryClientRepositorySpy repository;

	@Before
	public void setUp() {
		objectMother = new __TestObjectMother();
		repository = new InMemoryClientRepositorySpy();
		model = new ClientRepositoryComboBoxModel(repository);
	}

	@Test
	public void getSelectedItemShouldReturnSelectedItem() {
		model.setSelectedItem(objectMother.myCompany());
		assertEquals(objectMother.myCompany(), model.getSelectedItem());
	}

	@Test
	public void listenersShouldBeNotifiedOnSetSelectItem() {
		ListDataListenerSpy listener = new ListDataListenerSpy();
		model.addListDataListener(listener);
		assertFalse(listener.contentsChangedCalled);
		model.setSelectedItem(objectMother.myCompany());
		assertTrue(listener.contentsChangedCalled);
	}

	@Test
	public void sizeShouldChangeWhenClientIsAddedToRepository() {
		assertEquals(0, model.getSize());
		repository.add(objectMother.myCompany());
		assertEquals(1, model.getSize());
	}

	@Test
	public void getItemAtShouldReturnTheClientsInRepository() {
		repository.add(objectMother.myCompany());
		repository.add(objectMother.otherCompany());
		CustomModelAsserts.assertClientEquals(objectMother.myCompany(), model.getElementAt(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void setSelectedItemShouldThrowExceptionIfNotPresent() {
		model.setSelectedItem("");
	}

	@Test
	public void shouldHaveInternalCacheOfClients() {
		assertEquals(0, repository.getAllCalledCount);
		model.getSize();
		assertEquals(1, repository.getAllCalledCount);
		model.getSize();
		assertEquals(1, repository.getAllCalledCount);
		repository.add(objectMother.myCompany());
		model.getSize();
		assertEquals(2, repository.getAllCalledCount);
	}

}

package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.CustomModelAsserts;
import com.aldercape.internal.economics.persistence.InMemoryColaboratorRepository;

public class ColaboratorRepositoryComboBoxModelTest {

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

	public class InMemoryColaboratorRepositorySpy extends InMemoryColaboratorRepository {

		public int getAllCalledCount;

		@Override
		public List<Collaborator> getAll() {
			getAllCalledCount++;
			return super.getAll();
		}
	}

	private __TestObjectMother objectMother;
	private CollaboratorRepositoryComboBoxModel model;
	private InMemoryColaboratorRepositorySpy repository;

	@Before
	public void setUp() {
		objectMother = new __TestObjectMother();
		repository = new InMemoryColaboratorRepositorySpy();
		model = new CollaboratorRepositoryComboBoxModel(repository);
	}

	@Test
	public void getSelectedItemShouldReturnSelectedItem() {
		model.setSelectedItem(objectMother.me());
		assertEquals(objectMother.me(), model.getSelectedItem());
	}

	@Test
	public void listenersShouldBeNotifiedOnSetSelectItem() {
		ListDataListenerSpy listener = new ListDataListenerSpy();
		model.addListDataListener(listener);
		assertFalse(listener.contentsChangedCalled);
		model.setSelectedItem(objectMother.me());
		assertTrue(listener.contentsChangedCalled);
	}

	@Test
	public void sizeShouldChangeWhenClientIsAddedToRepository() {
		assertEquals(0, model.getSize());
		repository.add(objectMother.me());
		assertEquals(1, model.getSize());
	}

	@Test
	public void getItemAtShouldReturnTheClientsInRepository() {
		repository.add(objectMother.me());
		repository.add(objectMother.other());
		CustomModelAsserts.assertColaboratorEquals(objectMother.me(), model.getElementAt(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void setSelectedItemShouldThrowExceptionIfNotRightType() {
		model.setSelectedItem("");
	}

	@Test
	public void shouldHaveInternalCacheOfClients() {
		assertEquals(0, repository.getAllCalledCount);
		model.getSize();
		assertEquals(1, repository.getAllCalledCount);
		model.getSize();
		assertEquals(1, repository.getAllCalledCount);
		repository.add(objectMother.me());
		model.getSize();
		assertEquals(2, repository.getAllCalledCount);
	}

}

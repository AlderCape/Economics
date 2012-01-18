package com.aldercape.internal.economics.ui;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import com.aldercape.internal.economics.model.BaseRepository;
import com.aldercape.internal.economics.model.BaseRepository.Listener;

abstract class AbstractRepositoryComboBoxModel<T> extends AbstractListModel<T> implements ComboBoxModel<T>, Listener {

	private static final long serialVersionUID = -5740601701470413593L;

	protected T selectedItem;
	protected List<T> allItems;

	private BaseRepository<T> repository;

	public AbstractRepositoryComboBoxModel(BaseRepository<T> repository) {
		this.repository = repository;
		repository.addListener(this);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		assertClass(anItem);
		setObject(anItem);
		fireContentsChanged(this, 0, getSize());
	}

	@SuppressWarnings("unchecked")
	protected void setObject(Object anItem) {
		selectedItem = (T) anItem;
	}

	@Override
	public int getSize() {
		return getAllItems().size();
	}

	@Override
	public T getElementAt(int index) {
		return getAllItems().get(index);
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	protected List<T> getAllItems() {
		if (allItems == null) {
			allItems = readFromRepository();
		}
		return allItems;
	}

	protected List<T> readFromRepository() {
		return repository.getAll();
	}

	@Override
	public void changed() {
		allItems = null;
	}

	protected abstract void assertClass(Object anItem);
}

package com.aldercape.internal.economics.ui;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.ClientRepository.Listener;

public class ClientRepositoryComboBoxModel extends AbstractListModel<Client> implements ComboBoxModel<Client>, Listener {

	private static final long serialVersionUID = 3378891523813054700L;
	private Client selectedItem;
	private ClientRepository repository;
	private List<Client> clients;

	public ClientRepositoryComboBoxModel(ClientRepository repository) {
		this.repository = repository;
		repository.addListener(this);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		if (!(anItem instanceof Client)) {
			throw new IllegalArgumentException("Only clients can be stored got item of type " + anItem.getClass());
		}
		selectedItem = (Client) anItem;
		fireContentsChanged(this, 0, getSize());
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	@Override
	public int getSize() {
		return clients().size();
	}

	@Override
	public Client getElementAt(int index) {
		return clients().get(index);
	}

	private List<Client> clients() {
		if (clients == null) {
			clients = repository.getAll();
		}
		return clients;
	}

	@Override
	public void changed() {
		clients = null;
	}

	public Client getSelectedClient() {
		return selectedItem;
	}
}

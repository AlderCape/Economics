package com.aldercape.internal.economics.ui.client;

import com.aldercape.internal.economics.model.BaseRepository.Listener;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.ui.base.AbstractRepositoryComboBoxModel;

public class ClientRepositoryComboBoxModel extends AbstractRepositoryComboBoxModel<Client> implements Listener {

	private static final long serialVersionUID = 3378891523813054700L;

	public ClientRepositoryComboBoxModel(ClientRepository repository) {
		super(repository);
	}

	@Override
	protected void assertClass(Object anItem) {
		if (!(anItem instanceof Client)) {
			throw new IllegalArgumentException("Only clients can be stored got item of type " + anItem.getClass());
		}
	}

	public Client getSelectedClient() {
		return selectedItem;
	}

}

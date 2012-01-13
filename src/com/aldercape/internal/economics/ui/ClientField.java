package com.aldercape.internal.economics.ui;

import javax.swing.JComboBox;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Client.ClientRenderTarget;
import com.aldercape.internal.economics.model.ClientRepository;

public class ClientField extends JComboBox<Client> implements ClientRenderTarget {

	private static final long serialVersionUID = 354037197950309131L;
	private ClientRepositoryComboBoxModel model;

	public ClientField(ClientRepository clientRepository) {
		model = new ClientRepositoryComboBoxModel(clientRepository);
		setModel(model);
	}

	protected Client createDomainObject() {
		return model.getSelectedClient();
	}

	public Client getClient() {
		return createDomainObject();
	}

	@Override
	public void renderName(Client client) {
		model.setSelectedItem(client);
	}

}

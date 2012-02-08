package com.aldercape.internal.economics.ui;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Client.ClientRenderTarget;
import com.aldercape.internal.economics.model.ClientRepository;

public class ClientField extends JComboBox<Client> implements ClientRenderTarget {

	private static final long serialVersionUID = 354037197950309131L;
	private ClientRepositoryComboBoxModel model;

	public ClientField(ClientRepository clientRepository) {
		model = new ClientRepositoryComboBoxModel(clientRepository);
		setModel(model);
		setRenderer();
	}

	private void setRenderer() {
		setRenderer(new ListCellRenderer<Client>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends Client> list, Client value, int index, boolean isSelected, boolean cellHasFocus) {
				return new JLabel(value == null ? "" : value.name());
			}
		});
	}

	protected Client createDomainObject() {
		return model.getSelectedClient();
	}

	public Client getClient() {
		return createDomainObject();
	}

	@Override
	public void renderClient(Client client) {
		model.setSelectedItem(client);
	}

	@Override
	public void renderName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderContactPerson(String contactPerson) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderStreetName(String streetName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderStreetNumber(String streetNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderZipcode(String zipcode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderCity(String city) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderVatNumber(String vatNumber) {
		// TODO Auto-generated method stub

	}

}

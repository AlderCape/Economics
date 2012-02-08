package com.aldercape.internal.economics.ui;

import javax.swing.JLabel;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Address;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Client.ClientRenderTarget;

public class ClientPanel extends AbstractEntryPanel implements ClientRenderTarget {

	private static final long serialVersionUID = 4376682418152888271L;
	private StringTextField name;
	private StringTextField vat;
	private StringTextField contactPerson;
	private StringTextField street;
	private StringTextField number;
	private StringTextField zipcode;
	private StringTextField city;

	public ClientPanel(ApplicationModel model) {
		super(model);
		add(new JLabel("Name"));
		name = new StringTextField();
		add(name);

		add(new JLabel("Contact person"));
		contactPerson = new StringTextField();
		add(contactPerson);

		add(new JLabel("Street"));
		street = new StringTextField();
		add(street);

		add(new JLabel("Number"));
		number = new StringTextField();
		add(number);

		add(new JLabel("Zip code"));
		zipcode = new StringTextField();
		add(zipcode);

		add(new JLabel("City"));
		city = new StringTextField();
		add(city);

		add(new JLabel("Vat number"));
		vat = new StringTextField();
		add(vat);
	}

	@Override
	public void addEntry() {
		model.getClientRepository().add(createClient());

	}

	private Client createClient() {
		return new Client(name.getValue(), new Address(street.getValue(), number.getValue(), zipcode.getValue(), city.getValue()), vat.getValue(), contactPerson.getValue());
	}

	public void setEntry(Client client) {
		client.render(this);
	}

	@Override
	public void renderClient(Client client) {
	}

	@Override
	public void renderName(String name) {
		this.name.setValue(name);
	}

	@Override
	public void renderContactPerson(String contactPerson) {
		this.contactPerson.setValue(contactPerson);
	}

	@Override
	public void renderStreetName(String streetName) {
		this.street.setValue(streetName);
	}

	@Override
	public void renderStreetNumber(String streetNumber) {
		this.number.setValue(streetNumber);
	}

	@Override
	public void renderZipcode(String zipcode) {
		this.zipcode.setValue(zipcode);
	}

	@Override
	public void renderCity(String city) {
		this.city.setValue(city);
	}

	@Override
	public void renderVatNumber(String vatNumber) {
		this.vat.setValue(vatNumber);
	}

}

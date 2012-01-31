package com.aldercape.internal.economics.model;

public class Client {

	private String name;
	private String vatNumber;
	private Address address = new Address();
	private String contactPerson;

	public Client(String name, Address address, String vatNumber, String contactPerson) {
		this.name = name;
		this.address = address;
		this.vatNumber = vatNumber;
		this.contactPerson = contactPerson;
	}

	public String name() {
		return name;
	}

	public String vatNumber() {
		return vatNumber;
	}

	public String streetName() {
		return address.streetName();
	}

	public String streetNumber() {
		return address.streetNumber();
	}

	public String zipcode() {
		return address.zipcode();
	}

	public String city() {
		return address.city();
	}

	public String contactPerson() {
		return contactPerson;
	}

	public static interface ClientRenderTarget {

		public void renderClient(Client client);

		public void renderName(String name);

		public void renderContactPerson(String contactPerson);

		public void renderStreetName(String streetName);

		public void renderStreetNumber(String streetNumber);

		public void renderZipcode(String zipcode);

		public void renderCity(String city);

		public void renderVatNumber(String vatNumber);

	}

	public void render(ClientRenderTarget target) {
		target.renderClient(this);
		target.renderName(name());
		target.renderContactPerson(contactPerson());
		target.renderStreetName(streetName());
		target.renderStreetNumber(streetNumber());
		target.renderZipcode(zipcode());
		target.renderCity(city());
		target.renderVatNumber(vatNumber());
	}
}

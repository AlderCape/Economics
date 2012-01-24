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

	}

	public void render(ClientRenderTarget target) {
		target.renderClient(this);
		target.renderName(name());
	}

}

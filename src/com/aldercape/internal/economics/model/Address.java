package com.aldercape.internal.economics.model;

public class Address {
	private String streetName;
	private String streetNumber;
	private String zipcode;
	private String city;

	public Address() {
	}

	public Address(String streetName, String streetNumber, String zipcode, String city) {
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.zipcode = zipcode;
		this.city = city;
	}

	public String streetName() {
		return streetName;
	}

	public String streetNumber() {
		return streetNumber;
	}

	public String zipcode() {
		return zipcode;
	}

	public String city() {
		return city;
	}

}
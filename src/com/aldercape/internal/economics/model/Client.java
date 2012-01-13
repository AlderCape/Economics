package com.aldercape.internal.economics.model;

public class Client {

	public static final Client UNKNOWN = new Client("");
	private String name;

	public Client(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public static interface ClientRenderTarget {

		public void renderName(Client client);

	}

	public void render(ClientRenderTarget target) {
		target.renderName(this);
	}

}

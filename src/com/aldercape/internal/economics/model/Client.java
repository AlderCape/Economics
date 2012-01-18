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

		public void renderClient(Client client);

		public void renderName(String name);

	}

	public void render(ClientRenderTarget target) {
		target.renderClient(this);
		target.renderName(name());
	}

}

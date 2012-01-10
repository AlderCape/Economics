package com.aldercape.internal.economics.model;

public class Client {

	private String name;

	public Client(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public static interface ClientRenderTarget {

		public void renderName(String name);

	}

	public void render(ClientRenderTarget target) {
		target.renderName(name);
	}

}

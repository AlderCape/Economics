package com.aldercape.internal.economics.model;

public class Client implements SelfRenderable {

	private String name;

	public Client(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	@Override
	public void render(RenderTarget target) {
		target.setDisplayText(name);
	}

}

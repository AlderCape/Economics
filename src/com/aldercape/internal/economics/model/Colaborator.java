package com.aldercape.internal.economics.model;

public class Colaborator implements SelfRenderable {

	private String name;

	public Colaborator(String name) {
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

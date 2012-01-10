package com.aldercape.internal.economics.model;

public class Colaborator {

	private String name;

	public Colaborator(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public static interface ColaboratorRenderTarget {

		public void renderName(String name);
	}

	public void render(ColaboratorRenderTarget target) {
		target.renderName(name);
	}

}

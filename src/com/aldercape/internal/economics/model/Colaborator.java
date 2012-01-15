package com.aldercape.internal.economics.model;

public class Colaborator {

	public static final Colaborator UNKNOWN = new Colaborator("");
	private String name;

	public Colaborator(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public static interface ColaboratorRenderTarget {
		public void renderName(Colaborator name);
	}

	public void render(ColaboratorRenderTarget target) {
		target.renderName(this);
	}

}

package com.aldercape.internal.economics.model;

public class Collaborator {

	public static final Collaborator UNKNOWN = new Collaborator("");
	private String name;

	public Collaborator(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public static interface CollaboratorRenderTarget {
		public void renderName(Collaborator name);
	}

	public void render(CollaboratorRenderTarget target) {
		target.renderName(this);
	}

}

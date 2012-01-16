package com.aldercape.internal.economics.model;

public class Collaborator {

	public static final Collaborator UNKNOWN = new Collaborator("", "");
	private String name;
	private String firstname;
	private String lastname;

	public Collaborator(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
		name = firstname + " " + lastname;
		name = name.trim();
	}

	public String fullname() {
		return name;
	}

	public String firstname() {
		return firstname;
	}

	public String lastname() {
		return lastname;
	}

	public static interface CollaboratorRenderTarget {
		public void renderFullName(Collaborator name);

		void renderFirstName(String name);

		public void renderLastName(String name);

	}

	public void render(CollaboratorRenderTarget target) {
		target.renderFullName(this);
		target.renderFirstName(firstname());
		target.renderLastName(lastname());
	}

}

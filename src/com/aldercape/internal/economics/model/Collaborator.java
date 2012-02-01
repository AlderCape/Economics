package com.aldercape.internal.economics.model;

public class Collaborator implements Comparable<Collaborator> {

	public static final Collaborator UNKNOWN = new Collaborator("", "", "");
	private String firstname;
	private String lastname;
	private String email;

	public Collaborator(String firstname, String lastname, String email) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}

	public String fullname() {
		return firstname + " " + lastname;
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

		public void renderEmail(String email);

	}

	public void render(CollaboratorRenderTarget target) {
		target.renderFullName(this);
		target.renderFirstName(firstname());
		target.renderLastName(lastname());
		target.renderEmail(email());
	}

	@Override
	public int compareTo(Collaborator o) {
		return fullname().compareTo(o.fullname());
	}

	public String email() {
		return email;
	}

}

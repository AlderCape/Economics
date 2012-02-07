package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Collaborator;
import com.google.gson.JsonObject;

public class CollaboratorJsonDeserializer implements JsonModelDeserializer<Collaborator> {

	@Override
	public Collaborator deserialize(JsonObject collaborator) {
		String firstname = collaborator.get("firstname").getAsString();
		String lastname = collaborator.get("lastname").getAsString();
		String email = collaborator.get("email").getAsString();
		return new Collaborator(firstname, lastname, email);
	}

}

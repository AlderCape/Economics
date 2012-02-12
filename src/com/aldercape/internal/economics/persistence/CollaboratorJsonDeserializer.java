package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;

import com.aldercape.internal.economics.model.Collaborator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class CollaboratorJsonDeserializer implements JsonDeserializer<Collaborator> {

	@Override
	public Collaborator deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		String firstname = arg0.getAsJsonObject().get("firstname").getAsString();
		String lastname = arg0.getAsJsonObject().get("lastname").getAsString();
		String email = arg0.getAsJsonObject().get("email").getAsString();
		return new Collaborator(firstname, lastname, email);
	}

}

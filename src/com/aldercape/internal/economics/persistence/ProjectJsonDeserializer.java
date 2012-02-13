package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;

import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.Project;
import com.aldercape.internal.economics.model.ProjectMember;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ProjectJsonDeserializer implements JsonSerializer<Project>, JsonDeserializer<Project> {

	private ClientRepository clientRepository;
	private CollaboratorRepository collaboratorRepository;

	public ProjectJsonDeserializer(ClientRepository clientRepository, CollaboratorRepository collaboratorRepository) {
		this.clientRepository = clientRepository;
		this.collaboratorRepository = collaboratorRepository;
	}

	@Override
	public Project deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		return null;
	}

	@Override
	public JsonElement serialize(Project arg0, Type arg1, JsonSerializationContext arg2) {
		JsonObject result = new JsonObject();
		result.add("name", arg2.serialize(arg0.name()));
		result.add("client", arg2.serialize(clientRepository.getIdFor(arg0.client())));

		JsonArray membersArray = new JsonArray();
		for (ProjectMember projectMember : arg0.members()) {
			JsonObject member = new JsonObject();
			member.add("collaboratorId", arg2.serialize(collaboratorRepository.getIdFor(projectMember.getCollaborator())));
			member.add("rate", arg2.serialize(projectMember.rate()));
			membersArray.add(member);
		}
		result.add("members", membersArray);
		return result;
	}
}

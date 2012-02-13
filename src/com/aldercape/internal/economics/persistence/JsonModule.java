package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Address;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Invoice;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.Project;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.TimeEntryRepository;
import com.aldercape.internal.economics.model.Unit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonModule {

	private RepositoryRegistry repositoryRegistry;
	private boolean prettyPrinting;

	public JsonModule(RepositoryRegistry repositoryRegistry, boolean prettyPrinting) {
		this.repositoryRegistry = repositoryRegistry;
		this.prettyPrinting = prettyPrinting;
	}

	public void regiterOn(GsonBuilder gsonBuilder) {
		gsonBuilder.registerTypeAdapter(Day.class, new DayJsonDeserializer());
		gsonBuilder.registerTypeAdapter(Rate.class, new RateJsonDeserializer());
		gsonBuilder.registerTypeAdapter(Unit.class, new UnitJsonDeserializer());
		gsonBuilder.registerTypeAdapter(Address.class, new AddressJsonDeserializer());
		gsonBuilder.registerTypeAdapter(Client.class, new ClientJsonDeserializer());
		gsonBuilder.registerTypeAdapter(Collaborator.class, new CollaboratorJsonDeserializer());
		gsonBuilder.registerTypeAdapter(InvoiceEntry.class, new InvoiceEntryDeserializer(getTimeEntryRepository()));
		gsonBuilder.registerTypeAdapter(TimeEntry.class, new TimeEntryJsonDeserializer(getCollaboratorRepository(), getClientRepository()));
		gsonBuilder.registerTypeAdapter(Invoice.class, new InvoiceJsonDeserializer());
		gsonBuilder.registerTypeAdapter(Project.class, new ProjectJsonDeserializer(getClientRepository(), getCollaboratorRepository()));
	}

	public Gson createJsonEngine() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		regiterOn(gsonBuilder);
		if (prettyPrinting) {
			gsonBuilder = gsonBuilder.setPrettyPrinting();
		}
		Gson json = gsonBuilder.create();
		return json;
	}

	private TimeEntryRepository getTimeEntryRepository() {
		return repositoryRegistry.getRepository(TimeEntryRepository.class);
	}

	private ClientRepository getClientRepository() {
		return repositoryRegistry.getRepository(ClientRepository.class);
	}

	private CollaboratorRepository getCollaboratorRepository() {
		return repositoryRegistry.getRepository(CollaboratorRepository.class);
	}

}

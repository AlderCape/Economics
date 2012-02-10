package com.aldercape.internal.economics.persistence;

import com.aldercape.internal.economics.model.Address;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.persistence.InvoiceEntryFileSystemRepository.InvoiceEntryJson;
import com.aldercape.internal.economics.persistence.InvoiceFileSystemRepository.InvoiceJson;
import com.aldercape.internal.economics.persistence.TimeEntryFileSystemRepository.TimeEntryJson;
import com.google.gson.GsonBuilder;

public class JsonModule {

	public void regiterOn(GsonBuilder gsonBuilder) {
		gsonBuilder.registerTypeAdapter(Day.class, new DayJsonDeserializer());
		gsonBuilder.registerTypeAdapter(Rate.class, new RateJsonDeserializer());
		gsonBuilder.registerTypeAdapter(Unit.class, new UnitJsonDeserializer());
		gsonBuilder.registerTypeAdapter(Address.class, new AddressJsonDeserializer());
		gsonBuilder.registerTypeAdapter(Client.class, new ClientJsonDeserializer());
		gsonBuilder.registerTypeAdapter(Collaborator.class, new CollaboratorJsonDeserializer());
		gsonBuilder.registerTypeAdapter(InvoiceEntryJson.class, new InvoiceEntryDeserializer());
		gsonBuilder.registerTypeAdapter(TimeEntryJson.class, new TimeEntryJsonDeserializer());
		gsonBuilder.registerTypeAdapter(InvoiceJson.class, new InvoiceJsonDeserializer());
	}

}

package com.aldercape.internal.economics.persistence;

import java.util.ArrayList;
import java.util.List;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.persistence.InvoiceFileSystemRepository.InvoiceJson;
import com.aldercape.internal.economics.persistence.InvoiceFileSystemRepository.InvoiceJson.InvoiceItemJson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class InvoiceJsonDeserializer implements JsonModelDeserializer<InvoiceJson> {

	private JsonModule jsonModule;

	public InvoiceJsonDeserializer(JsonModule jsonModule) {
		this.jsonModule = jsonModule;
	}

	@Override
	public InvoiceJson deserialize(JsonObject jsonObj) {
		Client company = (Client) jsonModule.getDeserializer("client").deserialize(jsonObj.get("company").getAsJsonObject());
		Client client = (Client) jsonModule.getDeserializer("client").deserialize(jsonObj.get("client").getAsJsonObject());
		Day issueDate = (Day) jsonModule.getDeserializer("day").deserialize(jsonObj.get("issueDate").getAsJsonObject());
		Day dueDate = (Day) jsonModule.getDeserializer("day").deserialize(jsonObj.get("issueDate").getAsJsonObject());
		JsonArray asJsonArray = jsonObj.get("entries").getAsJsonArray();
		List<InvoiceItemJson> entries = new ArrayList<>();
		for (JsonElement jsonElement : asJsonArray) {
			Unit units = (Unit) jsonModule.getDeserializer("unit").deserialize(jsonElement.getAsJsonObject().get("units").getAsJsonObject());
			Rate rate = (Rate) jsonModule.getDeserializer("rate").deserialize(jsonElement.getAsJsonObject().get("rate").getAsJsonObject());
			long id = jsonElement.getAsJsonObject().get("id").getAsLong();
			String description = "";
			String title = "";
			entries.add(new InvoiceItemJson(title, description, units, rate, id));
		}
		return new InvoiceJson(company, client, issueDate, dueDate, entries);
	}
}

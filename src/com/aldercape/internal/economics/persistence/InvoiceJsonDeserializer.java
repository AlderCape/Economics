package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;
import java.util.List;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.persistence.InvoiceFileSystemRepository.InvoiceJson;
import com.aldercape.internal.economics.persistence.InvoiceFileSystemRepository.InvoiceJson.InvoiceItemJson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class InvoiceJsonDeserializer implements JsonDeserializer<InvoiceJson> {

	@Override
	public InvoiceJson deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		JsonObject jsonObj = arg0.getAsJsonObject();
		Client company = arg2.deserialize(jsonObj.get("company"), Client.class);
		Client client = arg2.deserialize(jsonObj.get("client"), Client.class);
		Day issueDate = arg2.deserialize(jsonObj.get("issueDate"), Day.class);
		Day dueDate = arg2.deserialize(jsonObj.get("dueDate"), Day.class);
		List<InvoiceItemJson> entries = arg2.deserialize(jsonObj.get("entries").getAsJsonArray(), new TypeToken<List<InvoiceItemJson>>() {
		}.getType());
		return new InvoiceJson(company, client, issueDate, dueDate, entries);
	}
}

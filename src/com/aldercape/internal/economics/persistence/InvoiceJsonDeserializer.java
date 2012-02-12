package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;
import java.util.List;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Invoice;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class InvoiceJsonDeserializer implements JsonDeserializer<Invoice>, JsonSerializer<Invoice> {

	@Override
	public Invoice deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		JsonObject jsonObj = arg0.getAsJsonObject();
		Client company = arg2.deserialize(jsonObj.get("company"), Client.class);
		Client client = arg2.deserialize(jsonObj.get("client"), Client.class);
		Day issueDate = arg2.deserialize(jsonObj.get("issueDate"), Day.class);
		Day dueDate = arg2.deserialize(jsonObj.get("dueDate"), Day.class);
		List<InvoiceEntry> entries = arg2.deserialize(jsonObj.get("entries").getAsJsonArray(), new TypeToken<List<InvoiceEntry>>() {
		}.getType());
		return new Invoice(company, client, issueDate, entries, issueDate.daysBetween(dueDate));
	}

	@Override
	public JsonElement serialize(Invoice arg0, Type arg1, JsonSerializationContext arg2) {
		JsonObject result = new JsonObject();
		result.add("company", arg2.serialize(arg0.company(), Client.class));
		result.add("client", arg2.serialize(arg0.client(), Client.class));
		result.add("issueDate", arg2.serialize(arg0.issueDate(), Day.class));
		result.add("dueDate", arg2.serialize(arg0.dueDate(), Day.class));
		result.add("entries", arg2.serialize(arg0.entries(), new TypeToken<List<InvoiceEntry>>() {
		}.getType()));
		return result;
	}
}

package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.aldercape.internal.economics.model.ComposedInvoiceEntry;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.InvoiceEntryGroupingRule;
import com.aldercape.internal.economics.model.TimeEntryRepository;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class InvoiceEntryDeserializer implements JsonDeserializer<InvoiceEntry>, JsonSerializer<InvoiceEntry> {

	private TimeEntryRepository repository;

	public InvoiceEntryDeserializer(TimeEntryRepository repository) {
		this.repository = repository;
	}

	@Override
	public InvoiceEntry deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		Set<Long> ids = new LinkedHashSet<Long>();
		for (JsonElement jsonElement : arg0.getAsJsonObject().get("timeEntries").getAsJsonArray()) {
			ids.add(jsonElement.getAsLong());
		}
		return new ComposedInvoiceEntry(new InvoiceEntryGroupingRule(), repository.findByIds(ids));
	}

	@Override
	public JsonElement serialize(InvoiceEntry arg0, Type arg1, JsonSerializationContext arg2) {
		Map<String, Set<Long>> idsFor = new HashMap<>();
		idsFor.put("timeEntries", repository.getIdsFor(arg0.getAllEntries()));
		return arg2.serialize(idsFor);
	}
}

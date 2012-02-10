package com.aldercape.internal.economics.persistence;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

import com.aldercape.internal.economics.persistence.InvoiceEntryFileSystemRepository.InvoiceEntryJson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class InvoiceEntryDeserializer implements JsonDeserializer<InvoiceEntryJson> {

	@Override
	public InvoiceEntryJson deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		Set<Long> ids = new LinkedHashSet<Long>();
		for (JsonElement jsonElement : arg0.getAsJsonObject().get("timeEntries").getAsJsonArray()) {
			ids.add(jsonElement.getAsLong());
		}
		return new InvoiceEntryJson(ids);
	}
}

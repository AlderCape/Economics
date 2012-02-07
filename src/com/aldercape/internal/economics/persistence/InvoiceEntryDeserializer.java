package com.aldercape.internal.economics.persistence;

import java.util.LinkedHashSet;
import java.util.Set;

import com.aldercape.internal.economics.persistence.InvoiceEntryFileSystemRepository.InvoiceEntryJson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class InvoiceEntryDeserializer implements JsonModelDeserializer<InvoiceEntryJson> {

	@Override
	public InvoiceEntryJson deserialize(JsonObject jsonObject) {
		Set<Long> ids = new LinkedHashSet<Long>();
		for (JsonElement jsonElement : jsonObject.get("timeEntries").getAsJsonArray()) {
			ids.add(jsonElement.getAsLong());
		}
		return new InvoiceEntryJson(ids);
	}

}

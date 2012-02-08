package com.aldercape.internal.economics.persistence;

import java.util.HashMap;
import java.util.Map;

public class JsonModule {

	Map<String, JsonModelDeserializer<?>> deserializers = new HashMap<>();

	public JsonModule(JsonStorage<?> storage) {
		deserializers.put("rate", new RateJsonDeserialize());
		deserializers.put("unit", new UnitJsonDeserializer());
		deserializers.put("day", new DayJsonDeserializer());
		deserializers.put("address", new AddressJsonDeserializer());
		deserializers.put("client", new ClientJsonDeserializer(this));
		deserializers.put("collaborator", new CollaboratorJsonDeserializer());
		deserializers.put("invoiceEntry", new InvoiceEntryDeserializer());
		deserializers.put("timeEntry", new TimeEntryJsonDeserializer(this));
		deserializers.put("invoice", new InvoiceJsonDeserializer(this));

	}

	public JsonModelDeserializer<?> getDeserializer(String mainType) {
		return deserializers.get(mainType);
	}

}

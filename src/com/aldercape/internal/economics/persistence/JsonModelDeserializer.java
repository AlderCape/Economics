package com.aldercape.internal.economics.persistence;

import com.google.gson.JsonObject;

public interface JsonModelDeserializer<T> {

	public T deserialize(JsonObject jsonObj);

}

package com.aldercape.internal.economics.model;

public class EntryNotForClientException extends RuntimeException {

	private static final long serialVersionUID = 1368774761278920705L;

	public EntryNotForClientException(String message) {
		super(message);
	}

	public EntryNotForClientException(Throwable cause) {
		super(cause);
	}

	public EntryNotForClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntryNotForClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

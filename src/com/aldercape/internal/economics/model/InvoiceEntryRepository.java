package com.aldercape.internal.economics.model;

public interface InvoiceEntryRepository {

	public long getIdFor(InvoiceEntry e);

	public InvoiceEntry getById(long id);

	public void add(InvoiceEntry entry);

}

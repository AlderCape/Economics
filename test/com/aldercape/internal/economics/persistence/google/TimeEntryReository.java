package com.aldercape.internal.economics.persistence.google;

import java.util.List;

import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;

public interface TimeEntryReository {

	public List<Entry<Day>> findAll(Collaborator me);

	public void add(Entry<Day> entry);

}

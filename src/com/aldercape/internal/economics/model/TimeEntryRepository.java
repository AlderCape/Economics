package com.aldercape.internal.economics.model;

import java.util.Set;

public interface TimeEntryRepository {

	public Set<TimeEntry> findByIds(Set<Long> timeEntries);

	public Set<Long> getIdsFor(Set<TimeEntry> allEntries);

	public void add(TimeEntry entry);

}

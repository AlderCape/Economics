package com.aldercape.internal.economics.model;

import java.util.Comparator;

public interface Rule<T> {

	public EntryCriteria<Day> getCriteria(T example);

	public Comparator<T> getComparator();

}

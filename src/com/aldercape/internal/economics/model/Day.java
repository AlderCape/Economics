package com.aldercape.internal.economics.model;

public class Day implements Comparable<Day> {

	private int day;
	private Month month;

	public Day(int day, Month month) {
		this.day = day;
		this.month = month;
	}

	public static Day january(int day, int year) {
		return new Day(day, Month.january(year));
	}

	public static Day february(int day, int year) {
		return new Day(day, Month.february(year));
	}

	public static Day mars(int day, int year) {
		return new Day(day, Month.mars(year));
	}

	public static Day april(int day, int year) {
		return new Day(day, Month.april(year));
	}

	public static Day may(int day, int year) {
		return new Day(day, Month.may(year));
	}

	public static Day june(int day, int year) {
		return new Day(day, Month.june(year));
	}

	public static Day july(int day, int year) {
		return new Day(day, Month.july(year));
	}

	public static Day august(int day, int year) {
		return new Day(day, Month.august(year));
	}

	public static Day september(int day, int year) {
		return new Day(day, Month.september(year));
	}

	public static Day october(int day, int year) {
		return new Day(day, Month.october(year));
	}

	public static Day november(int day, int year) {
		return new Day(day, Month.november(year));
	}

	public static Day december(int day, int year) {
		return new Day(day, Month.december(year));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		Day other = (Day) obj;
		if (!month.equals(other.month)) {
			return false;
		}
		return day == other.day;
	}

	@Override
	public int hashCode() {
		return day ^ month.hashCode();
	}

	@Override
	public String toString() {
		return day + " " + month;
	}

	@Override
	public int compareTo(Day arg0) {
		int result = month.compareTo(arg0.month);
		if (result != 0) {
			return result;
		}
		return day - arg0.day;
	}

	public Month month() {
		return month;
	}
}

package com.aldercape.internal.economics.model;

public class Month extends TimePoint implements Comparable<Month> {

	private MonthLiteral month;
	private int year;

	public Month(MonthLiteral month, int year) {
		this.month = month;
		this.year = year;
	}

	public String shortDisplayString() {
		return month + " - " + year;
	}

	@Override
	public String toString() {
		return shortDisplayString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Month)) {
			return false;
		}
		Month other = (Month) obj;
		return month == other.month && year == other.year;
	}

	@Override
	public int hashCode() {
		return month.hashCode() ^ year;
	}

	public static Month january(int year) {
		return new Month(MonthLiteral.January, year);
	}

	public static Month february(int year) {
		return new Month(MonthLiteral.February, year);
	}

	public static Month mars(int year) {
		return new Month(MonthLiteral.Mars, year);
	}

	public static Month april(int year) {
		return new Month(MonthLiteral.April, year);
	}

	public static Month may(int year) {
		return new Month(MonthLiteral.May, year);
	}

	public static Month june(int year) {
		return new Month(MonthLiteral.June, year);
	}

	public static Month july(int year) {
		return new Month(MonthLiteral.July, year);
	}

	public static Month august(int year) {
		return new Month(MonthLiteral.August, year);
	}

	public static Month september(int year) {
		return new Month(MonthLiteral.September, year);
	}

	public static Month october(int year) {
		return new Month(MonthLiteral.October, year);
	}

	public static Month november(int year) {
		return new Month(MonthLiteral.November, year);
	}

	public static Month december(int year) {
		return new Month(MonthLiteral.December, year);
	}

	@Override
	public int compareTo(Month o) {
		if (year != o.year)
			return year - o.year;
		return month.compareTo(o.month);
	}

	public static Month createFrom(MonthLiteral month, int year) {
		return new Month(month, year);
	}

	public MonthLiteral month() {
		return month;
	}

	public int year() {
		return year;
	}

	public static interface MonthTargetRenderer {

		public void renderMonth(MonthLiteral month);

		public void renderYear(int year);

	}

	public void render(MonthTargetRenderer target) {
		target.renderMonth(month());
		target.renderYear(year());
	}

}

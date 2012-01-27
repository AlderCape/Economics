package com.aldercape.internal.economics.model;

import java.util.Calendar;

public class Day extends TimePoint implements Comparable<Day> {

	public static final Day LAST_DAY = new Day(-1, Month.january(1000));
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
		if (this == arg0) {
			return 0;
		}
		if (this == LAST_DAY) {
			return 1;
		}

		if (arg0 == LAST_DAY) {
			return -1;
		}

		int result = month.compareTo(arg0.month);
		if (result != 0) {
			return result;
		}
		return day - arg0.day;
	}

	@Override
	public Month month() {
		return month;
	}

	public static Day createFrom(MonthLiteral month, int day, int year) {
		return new Day(day, Month.createFrom(month, year));
	}

	public static interface DayRenderTarget {

		public void renderDay(int day);

		public void renderMonth(MonthLiteral month);

		public void renderYear(int year);

	}

	public void render(DayRenderTarget target) {
		target.renderDay(day);
		target.renderMonth(month.monthLitteral());
		target.renderYear(month.year());
	}

	public boolean sameMonth(Day other) {
		return month().equals(other.month);
	}

	public Day daysAfter(int days) {
		Month newMonth = month;
		int nextDayInMonth = day + days;
		while (nextDayInMonth > newMonth.daysInMonth()) {
			nextDayInMonth = nextDayInMonth - newMonth.daysInMonth();
			newMonth = newMonth.nextMonth();
		}
		return new Day(nextDayInMonth, newMonth);
	}

	public boolean after(Day other) {
		return compareTo(other) > 0;
	}

	public static Day fromLong(long value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(value);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		return new Day(dayOfMonth, Month.fromLong(value));
	}

}

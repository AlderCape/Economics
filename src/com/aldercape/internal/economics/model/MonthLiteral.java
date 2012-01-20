package com.aldercape.internal.economics.model;

import java.util.GregorianCalendar;

public enum MonthLiteral {

	January(31), February(28) {
		@Override
		public int daysInMonth(int year) {
			GregorianCalendar cal = new GregorianCalendar();
			if (cal.isLeapYear(year)) {
				return 29;
			}
			return super.daysInMonth(year);
		}
	},
	Mars(31), April(30), May(31), June(30), July(31), August(31), September(30), October(31), November(30), December(31);

	private int daysInMonth;

	private MonthLiteral(int daysInMonth) {
		this.daysInMonth = daysInMonth;
	}

	public int daysInMonth(int year) {
		return daysInMonth;
	}

	public MonthLiteral next() {
		return MonthLiteral.values()[(ordinal() + 1) % values().length];
	}
}
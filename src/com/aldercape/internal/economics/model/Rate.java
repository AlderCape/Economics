package com.aldercape.internal.economics.model;

public class Rate {

	private static final int HOURS_PER_DAY = 8;

	private Euro amount;

	private Rate(Euro amount) {
		this.amount = amount;
	}

	public static Rate daily(Euro amount) {
		return new Rate(amount);
	}

	public static Rate hourly(Euro amount) {
		return daily(amount.times(8));
	}

	public Euro costPerDay() {
		return amount;
	}

	public Euro costPerHour() {
		return amount.divide(HOURS_PER_DAY);
	}

}

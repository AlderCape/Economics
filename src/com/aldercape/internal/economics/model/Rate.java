package com.aldercape.internal.economics.model;

public class Rate implements Comparable<Rate> {

	private static final int HOURS_PER_DAY = 8;

	private Euro amount;

	private Rate(Euro amount) {
		this.amount = amount;
	}

	public static Rate daily(Euro amount) {
		return new Rate(amount);
	}

	public static Rate daily(int amount) {
		return daily(new Euro(amount));
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

	@Override
	public String toString() {
		return costPerDay() + "/day";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Rate)) {
			return false;
		}
		Rate other = (Rate) obj;
		return other.costPerDay().equals(costPerDay());
	}

	@Override
	public int hashCode() {
		return costPerDay().hashCode();
	}

	@Override
	public int compareTo(Rate rate) {
		return costPerDay().compareTo(rate.costPerDay());
	}

	public static interface RateRenderTarget {
		public void renderAmount(Euro amount);
	}

	public void render(RateRenderTarget target) {
		target.renderAmount(amount);
	}

}

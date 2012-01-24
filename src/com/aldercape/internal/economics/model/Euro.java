package com.aldercape.internal.economics.model;

public class Euro implements Comparable<Euro> {

	private int amount;

	public Euro(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "(EUR) " + amount;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Euro)) {
			return false;
		}
		Euro other = (Euro) obj;
		return amount == other.amount;
	}

	@Override
	public int hashCode() {
		return amount;
	}

	public Euro times(int multiple) {
		return new Euro(amount * multiple);
	}

	public Euro percentage(int percent) {
		return new Euro((amount * percent) / 100);
	}

	public Euro plus(Euro other) {
		return new Euro(amount + other.amount);
	}

	public static Euro createFrom(int i) {
		return new Euro(i);
	}

	@Override
	public int compareTo(Euro rate) {
		return amount - rate.amount;
	}

	public static interface EuroRenderTarget {
		public void renderAmount(int amount);
	}

	public void render(EuroRenderTarget target) {
		target.renderAmount(amount);
	}

}

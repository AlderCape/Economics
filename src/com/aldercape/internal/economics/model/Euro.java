package com.aldercape.internal.economics.model;

public class Euro implements SelfRenderable, Comparable<Euro> {

	private int amount;

	public Euro(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Û " + amount;
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

	public static Euro createFrom(String text) {
		if (text.charAt(0) == 'Û') {
			text = text.substring(1);
		}
		return new Euro(Integer.parseInt(text.trim()));
	}

	@Override
	public void render(RenderTarget target) {
		target.setDisplayText("" + amount);
	}

	@Override
	public int compareTo(Euro rate) {
		return amount - rate.amount;
	}

}

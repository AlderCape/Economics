package com.aldercape.internal.economics.model;

public class Unit {

	int amount;
	private TimeUnit unit;

	public Unit(int amount, TimeUnit unit) {
		this.amount = amount;
		this.unit = unit;
	}

	public int days() {
		return unit.inDays(this);
	}

	public int hours() {
		return unit.inHours(this);
	}

	public static Unit days(int days) {
		return new Unit(days, TimeUnit.DAY);
	}

	public static Unit hours(int hours) {
		return new Unit(hours, TimeUnit.HOUR);
	}

	@Override
	public String toString() {
		return days() + " days";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Unit))
			return false;
		Unit other = (Unit) obj;
		return hours() == other.hours();
	}

	@Override
	public int hashCode() {
		return hours();
	}

	public static Unit createFrom(int amount) {
		return days(amount);
	}

	public Unit plus(Unit units) {
		return new Unit(days() + units.days(), TimeUnit.DAY);
	}

	public static interface UnitRenderTarget {

		public void renderAmount(int amount);

		public void renderTimeUnit(TimeUnit unit);
	}

	public void render(UnitRenderTarget target) {
		target.renderAmount(amount);
		target.renderTimeUnit(unit);
	}

}

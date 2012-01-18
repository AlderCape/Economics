package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.TimeUnit;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.model.Unit.UnitRenderTarget;

public class UnitTextField extends IntTextField implements UnitRenderTarget {

	private static final long serialVersionUID = 3552280438583529908L;

	public UnitTextField(Unit days) {
		days.render(this);
	}

	public UnitTextField() {
		this(Unit.days(0));
	}

	public Unit getUnits() {
		return createDomainObject();
	}

	protected Unit createDomainObject() {
		return Unit.createFrom(getValue());
	}

	@Override
	public void renderAmount(int amount) {
		setValue(amount);
	}

	@Override
	public void renderTimeUnit(TimeUnit unit) {
	}
}

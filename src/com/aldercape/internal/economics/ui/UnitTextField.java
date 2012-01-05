package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.Unit;

public class UnitTextField extends TextFieldRenderTarget<Unit> {

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

	@Override
	protected Unit createDomainObject() {
		return Unit.createFrom(getText());
	}
}

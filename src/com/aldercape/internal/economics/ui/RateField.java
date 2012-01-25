package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Euro.EuroRenderTarget;
import com.aldercape.internal.economics.model.Rate.RateRenderTarget;

public class RateField extends IntTextField implements RateRenderTarget, EuroRenderTarget {

	private static final long serialVersionUID = -125618017022952442L;

	public RateField(Euro euro) {
		euro.render(this);
	}

	public Euro getEuros() {
		return createDomainObject();
	}

	protected Euro createDomainObject() {
		return Euro.createFrom(getValue());
	}

	@Override
	public void renderAmount(int amount) {
		setValue(amount);
	}

	@Override
	public void renderAmount(Euro amount) {
		amount.render(this);
	}
}

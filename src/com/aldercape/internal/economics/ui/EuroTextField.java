package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Euro.EuroRenderTarget;

public class EuroTextField extends IntTextField implements EuroRenderTarget {

	private static final long serialVersionUID = -125618017022952442L;

	public EuroTextField(Euro euro) {
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
}

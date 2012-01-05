package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.Euro;

public class EuroTextField extends TextFieldRenderTarget<Euro> {

	private static final long serialVersionUID = -125618017022952442L;

	public EuroTextField(Euro euro) {
		euro.render(this);
	}

	public Euro getEuros() {
		return createDomainObject();
	}

	@Override
	protected Euro createDomainObject() {
		return Euro.createFrom(getText());
	}
}

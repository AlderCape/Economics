package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.Colaborator;
import com.aldercape.internal.economics.model.Colaborator.ColaboratorRenderTarget;

public class ColaboratorTextField extends StringTextField implements ColaboratorRenderTarget {

	private static final long serialVersionUID = -3265905259908228386L;

	protected Colaborator createDomainObject() {
		return new Colaborator(getValue());
	}

	@Override
	public void renderName(String name) {
		setValue(name);
	}

}

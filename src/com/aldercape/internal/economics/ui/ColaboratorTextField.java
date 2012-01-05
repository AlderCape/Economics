package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.Colaborator;

public class ColaboratorTextField extends TextFieldRenderTarget<Colaborator> {

	private static final long serialVersionUID = -3265905259908228386L;

	public Colaborator getColaborator() {
		return createDomainObject();
	}

	@Override
	protected Colaborator createDomainObject() {
		return new Colaborator(getText());
	}

}

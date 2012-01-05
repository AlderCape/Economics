package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.RenderTarget;

public class ClientTextField extends TextFieldRenderTarget<Client> implements RenderTarget {

	private static final long serialVersionUID = 354037197950309131L;

	@Override
	public void setDisplayText(String text) {
		setText(text);
	}

	@Override
	protected Client createDomainObject() {
		return new Client(getText());
	}

	public Client getClient() {
		return createDomainObject();
	}

}

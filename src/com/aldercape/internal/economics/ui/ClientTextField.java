package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Client.ClientRenderTarget;

public class ClientTextField extends StringTextField implements ClientRenderTarget {

	private static final long serialVersionUID = 354037197950309131L;

	protected Client createDomainObject() {
		return new Client(getValue());
	}

	public Client getClient() {
		return createDomainObject();
	}

	@Override
	public void renderName(String name) {
		setValue(name);
	}

}

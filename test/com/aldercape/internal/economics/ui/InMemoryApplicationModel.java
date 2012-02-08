package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.persistence.InMemoryClientRepository;
import com.aldercape.internal.economics.persistence.InMemoryCollaboratorRepository;

public class InMemoryApplicationModel extends ApplicationModel {

	public InMemoryApplicationModel(Ledger ledger) {
		super(ledger);
		collaboratorRepository = new InMemoryCollaboratorRepository();
		clientRepository = new InMemoryClientRepository();
	}

}

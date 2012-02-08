package com.aldercape.internal.economics;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.aldercape.internal.economics.model.AbstractEntry;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.model.LedgerListener;
import com.aldercape.internal.economics.persistence.ClientFileSystemRepository;
import com.aldercape.internal.economics.persistence.CollaboratorFileSystemRepository;
import com.aldercape.internal.economics.persistence.InMemoryClientRepository;

public class ApplicationModel {

	private List<LedgerListener> ledgerListeners = new ArrayList<LedgerListener>();
	private Ledger ledger;
	protected ClientRepository clientRepository = new ClientFileSystemRepository("clients.json");
	protected CollaboratorRepository collaboratorRepository = new CollaboratorFileSystemRepository("collaborators.json");

	public ApplicationModel(Ledger ledger) {
		this.ledger = ledger;
	}

	public void addEntry(AbstractEntry<Day> entry) {
		ledger.addEntry(entry);
		notifyListeners();
	}

	private void notifyListeners() {
		for (LedgerListener listener : ledgerListeners) {
			listener.ledgerUpdated();
		}
	}

	public void addLedgerListner(LedgerListener ledgerListener) {
		this.ledgerListeners.add(ledgerListener);

	}

	public Ledger ledger() {
		return ledger;
	}

	public void removeEntries(Set<? extends Entry<Day>> entries) {
		ledger.removeEntries(entries);
		notifyListeners();
	}

	public void addEntries(Set<? extends Entry<Day>> toAdd) {
		ledger.addEntries(toAdd);
		notifyListeners();
	}

	public ClientRepository getClientRepository() {
		return clientRepository;
	}

	public CollaboratorRepository getCollaboratorRepository() {
		return collaboratorRepository;
	}

	public void setClientRepository(InMemoryClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public void setCollaboraorRepository(CollaboratorRepository inMemoryClientRepository) {
		this.collaboratorRepository = inMemoryClientRepository;
	}

}

package com.aldercape.internal.economics.persistence;

import java.io.File;

import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.InvoiceEntryRepository;
import com.aldercape.internal.economics.model.InvoiceRepository;
import com.aldercape.internal.economics.model.TimeEntryRepository;

public class __FileSystemRepositories {

	private InvoiceRepository invoiceRepository;
	private InvoiceEntryRepository invoiceEntryRepository;
	private CollaboratorRepository collaboratorRepository;
	private ClientRepository clientRepository;
	private TimeEntryRepository timeEntryRepository;

	public __FileSystemRepositories(File baseDir) {
		collaboratorRepository = new CollaboratorFileSystemRepository(new File(baseDir, "collaborators.json"));
		clientRepository = new ClientFileSystemRepository(new File(baseDir, "clients.json"));
		timeEntryRepository = new TimeEntryFileSystemRepository(new File(baseDir, "timeEntries.json"), collaboratorRepository, clientRepository);
		invoiceEntryRepository = new InvoiceEntryFileSystemRepository(new File(baseDir, "invoiceEntries.json"), timeEntryRepository);
		invoiceRepository = new InvoiceFileSystemRepository(new File(baseDir, "invoices.json"), invoiceEntryRepository, timeEntryRepository);
	}

	public InvoiceRepository invoiceRepository() {
		return invoiceRepository;
	}

	public InvoiceEntryRepository invoiceEntryRepository() {
		return invoiceEntryRepository;
	}

	public CollaboratorRepository collaboratorRepository() {
		return collaboratorRepository;
	}

	public ClientRepository clientRepository() {
		return clientRepository;
	}

	public TimeEntryRepository timeEntryRepository() {
		return timeEntryRepository;
	}
}

package com.aldercape.internal.economics.persistence;

import java.io.File;

public class __FileSystemRepositories {

	private InvoiceFileSystemRepository invoiceRepository;
	private InvoiceEntryFileSystemRepository invoiceEntryRepository;
	private CollaboratorFileSystemRepository collaboratorRepository;
	private ClientFileSystemRepository clientRepository;
	private TimeEntryFileSystemRepository timeEntryRepository;

	public __FileSystemRepositories(File baseDir) {
		collaboratorRepository = new CollaboratorFileSystemRepository(new File(baseDir, "collaborators.json"));
		clientRepository = new ClientFileSystemRepository(new File(baseDir, "clients.json"));
		timeEntryRepository = new TimeEntryFileSystemRepository(new File(baseDir, "timeEntries.json"), collaboratorRepository, clientRepository);
		invoiceEntryRepository = new InvoiceEntryFileSystemRepository(new File(baseDir, "invoiceEntries.json"), timeEntryRepository);
		invoiceRepository = new InvoiceFileSystemRepository(new File(baseDir, "invoices.json"), invoiceEntryRepository);
	}

	public InvoiceFileSystemRepository invoiceRepository() {
		return invoiceRepository;
	}

	public InvoiceEntryFileSystemRepository invoiceEntryRepository() {
		return invoiceEntryRepository;
	}

	public CollaboratorFileSystemRepository collaboratorRepository() {
		return collaboratorRepository;
	}

	public ClientFileSystemRepository clientRepository() {
		return clientRepository;
	}

	public TimeEntryFileSystemRepository timeEntryRepository() {
		return timeEntryRepository;
	}
}

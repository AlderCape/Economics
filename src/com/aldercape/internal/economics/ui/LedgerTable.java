package com.aldercape.internal.economics.ui;

import java.util.Set;

import javax.swing.JTable;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.InvoiceEntryBuilder;
import com.aldercape.internal.economics.model.TimeEntry;

public class LedgerTable extends JTable {

	private static final long serialVersionUID = -761812478508822254L;
	private LedgerTableModel model;

	public LedgerTable(ApplicationModel model) {
		this.model = new LedgerTableModel(model.ledger());
		setModel(this.model);
		model.addLedgerListner(this.model);
	}

	public Set<TimeEntry> createTimeEntriesFromSelection() {
		int[] selectedRows = getSelectedRows();
		return model.getRows(selectedRows);
	}

	public Set<? extends Entry<Day>> createInvoiceEntriesFromSelection() {
		return getInvoiceBuilder(createTimeEntriesFromSelection()).createInvoiceEntry();

	}

	private InvoiceEntryBuilder getInvoiceBuilder(Set<TimeEntry> entries) {
		return new InvoiceEntryBuilder(entries);
	}
}

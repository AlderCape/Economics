package com.aldercape.internal.economics.ui;

import javax.swing.table.AbstractTableModel;

import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.model.LedgerListener;

public class LedgerTableModel extends AbstractTableModel implements LedgerListener {

	private static final long serialVersionUID = 7624551540352648229L;

	enum Column {

		BookKeepingMonth("Bookkeeping month") {
			@Override
			public Object getValue(InvoiceEntry entry) {
				return entry.bookkeepingMonth();
			}
		},

		CashflowMonth("Cashflow month") {
			@Override
			public Object getValue(InvoiceEntry entry) {
				return entry.cashflowMonth();
			}
		},

		Person("Person") {
			@Override
			public Object getValue(InvoiceEntry entry) {
				return entry.colaborator().name();
			}
		},

		Client("Client") {
			@Override
			public Object getValue(InvoiceEntry entry) {
				return entry.client().name();
			}
		},

		Unit("Days") {

			@Override
			public Object getValue(InvoiceEntry entry) {
				return entry.units();
			}
		},

		Rate("Daily rate") {
			@Override
			public Object getValue(InvoiceEntry entry) {
				return entry.rate();
			}
		},

		Amount("Amount") {
			@Override
			public Object getValue(InvoiceEntry entry) {
				return entry.amount();
			}
		},

		Vat("Vat") {
			@Override
			public Object getValue(InvoiceEntry entry) {
				return entry.vat();
			}
		};

		private String colName;

		private Column(String colName) {
			this.colName = colName;
		}

		public abstract Object getValue(InvoiceEntry entry);

		public String columnTitle() {
			return colName;
		}
	}

	private Ledger ledger;

	public LedgerTableModel(Ledger ledger) {
		this.ledger = ledger;
	}

	@Override
	public int getColumnCount() {
		return Column.values().length;
	}

	@Override
	public int getRowCount() {
		return ledger.numberOfEntries();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getColumn(columnIndex).getValue(ledger.entry(rowIndex));
	}

	@Override
	public String getColumnName(int column) {
		return getColumn(column).columnTitle();
	}

	private Column getColumn(int columnIndex) {
		return Column.values()[columnIndex];
	}

	@Override
	public void ledgerUpdated() {
		fireTableDataChanged();
	}

}

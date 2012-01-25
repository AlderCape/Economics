package com.aldercape.internal.economics.ui;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.model.LedgerListener;
import com.aldercape.internal.economics.model.TimeEntry;

public class LedgerTableModel extends AbstractTableModel implements LedgerListener {

	private static final long serialVersionUID = 7624551540352648229L;

	enum Column {

		BookKeepingMonth("Bookkeeping month") {
			@Override
			public Object getValue(Entry<Day> entry) {
				return entry.getTimePoint().month();
			}
		},

		Person("Person") {
			@Override
			public Object getValue(Entry<Day> entry) {
				return entry.collaborator().fullname();
			}
		},

		Client("Client") {
			@Override
			public Object getValue(Entry<Day> entry) {
				return entry.client().name();
			}
		},

		Unit("Days") {

			@Override
			public Object getValue(Entry<Day> entry) {
				return entry.units();
			}
		},

		Rate("Daily rate") {
			@Override
			public Object getValue(Entry<Day> entry) {
				return entry.costPerDay();
			}
		},

		Amount("Amount") {
			@Override
			public Object getValue(Entry<Day> entry) {
				return entry.amount();
			}
		},

		Vat("Vat") {
			@Override
			public Object getValue(Entry<Day> entry) {
				return entry.vat();
			}
		};

		private String colName;

		private Column(String colName) {
			this.colName = colName;
		}

		public abstract Object getValue(Entry<Day> entry);

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

	public Set<TimeEntry> getRows(int[] rows) {
		Set<TimeEntry> result = new LinkedHashSet<TimeEntry>();
		for (int row : rows) {
			Entry<Day> entry = ledger.entry(row);
			if (entry instanceof TimeEntry) {
				result.add((TimeEntry) entry);
			}
		}
		return result;
	}

}

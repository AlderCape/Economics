package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Colaborator;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.model.Month;
import com.aldercape.internal.economics.model.Unit;

public class LedgerTableModelTest {

	private LedgerTableModel model;
	private Ledger ledger;
	private boolean tableChangedCalled;

	@Before
	public void setUp() {
		ledger = new Ledger();
		model = new LedgerTableModel(ledger);
	}

	@Test
	public void oneRowPerEntry() {
		assertEquals(0, model.getRowCount());
		ledger.addEntry(new InvoiceEntry(Unit.days(2), new Euro(10), new Colaborator("Me"), new Client("Client"), Month.february(2010), Month.mars(2010)));
		assertEquals(1, model.getRowCount());
	}

	@Test
	public void columnValues() {
		ledger.addEntry(new InvoiceEntry(Unit.days(2), new Euro(50), new Colaborator("Me"), new Client("Client"), Month.february(2010), Month.mars(2010)));
		assertEquals(Month.february(2010), model.getValueAt(0, 0));
		assertEquals(Month.mars(2010), model.getValueAt(0, 1));
		assertEquals("Me", model.getValueAt(0, 2));
		assertEquals("Client", model.getValueAt(0, 3));
		assertEquals(Unit.days(2), model.getValueAt(0, 4));
		assertEquals(new Euro(50), model.getValueAt(0, 5));
		assertEquals(new Euro(100), model.getValueAt(0, 6));
		assertEquals(new Euro(21), model.getValueAt(0, 7));
	}

	@Test
	public void columnCount() {
		assertEquals(8, model.getColumnCount());
		assertEquals("Bookkeeping month", model.getColumnName(0));
		assertEquals("Cashflow month", model.getColumnName(1));
		assertEquals("Person", model.getColumnName(2));
		assertEquals("Client", model.getColumnName(3));
		assertEquals("Days", model.getColumnName(4));
		assertEquals("Daily rate", model.getColumnName(5));
		assertEquals("Amount", model.getColumnName(6));
		assertEquals("Vat", model.getColumnName(7));

		try {
			model.getColumnName(model.getColumnCount());
			fail("Should throw a runtime exception");
		} catch (RuntimeException e) {
		}
	}

	@Test
	public void shouldFireTableChangedEventWhenLEdgerIsChanged() {
		model.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent arg0) {
				tableChangedCalled = true;
			}
		});
		assertFalse(tableChangedCalled);
		model.ledgerUpdated();
		assertTrue(tableChangedCalled);
	}
}

package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.AbstractEntry;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.model.Month;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.SimpleInvoiceEntry;
import com.aldercape.internal.economics.model.Unit;

public class LedgerTableModelTest {

	private LedgerTableModel model;
	private Ledger ledger;
	private boolean tableChangedCalled;
	private Collaborator me;
	private Client myCompany;

	@Before
	public void setUp() {
		__TestObjectMother objectMother = new __TestObjectMother();
		ledger = new Ledger();
		model = new LedgerTableModel(ledger);
		me = objectMother.me();
		myCompany = objectMother.myCompany();
	}

	@Test
	public void oneRowPerEntry() {
		assertEquals(0, model.getRowCount());
		AbstractEntry<Day> entry = new AbstractEntry<Day>(Unit.days(2), Rate.daily(new Euro(10)), me, myCompany, Day.february(1, 2010)) {
		};
		ledger.addEntry(entry);
		assertEquals(1, model.getRowCount());
	}

	@Test
	public void columnValues() {
		ledger.addEntry(new SimpleInvoiceEntry(Unit.days(2), Rate.daily(new Euro(50)), me, myCompany, Day.february(1, 2010)));
		assertEquals(Month.february(2010), model.getValueAt(0, 0));
		assertEquals("Me Surname", model.getValueAt(0, 1));
		assertEquals("My Company", model.getValueAt(0, 2));
		assertEquals(Unit.days(2), model.getValueAt(0, 3));
		assertEquals(Rate.daily(new Euro(50)), model.getValueAt(0, 4));
		assertEquals(new Euro(100), model.getValueAt(0, 5));
		assertEquals(new Euro(21), model.getValueAt(0, 6));
	}

	@Test
	public void columnCount() {
		assertEquals(7, model.getColumnCount());
		assertEquals("Bookkeeping month", model.getColumnName(0));
		assertEquals("Person", model.getColumnName(1));
		assertEquals("Client", model.getColumnName(2));
		assertEquals("Days", model.getColumnName(3));
		assertEquals("Daily rate", model.getColumnName(4));
		assertEquals("Amount", model.getColumnName(5));
		assertEquals("Vat", model.getColumnName(6));

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

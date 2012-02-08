package com.aldercape.internal.economics.ui;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Ledger;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.Unit;

public class LedgerFrameTest {

	private LedgerFrame frame;
	private ApplicationModel model;
	private Collaborator other;
	private Collaborator me;
	private Client myCompany;

	@Before
	public void setUp() {
		__TestObjectMother objectMother = new __TestObjectMother();
		myCompany = objectMother.myCompany();
		me = objectMother.me();
		other = objectMother.other();
		Ledger ledger = new Ledger();
		ledger.addEntry(new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), me, myCompany, Day.january(1, 2012)));
		ledger.addEntry(new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), me, myCompany, Day.january(2, 2012)));
		ledger.addEntry(new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), me, myCompany, Day.january(3, 2012)));
		ledger.addEntry(new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), other, myCompany, Day.january(1, 2012)));
		ledger.addEntry(new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), other, myCompany, Day.january(2, 2012)));
		ledger.addEntry(new TimeEntry(Unit.days(1), Rate.daily(new Euro(200)), other, myCompany, Day.january(3, 2012)));
		model = new InMemoryApplicationModel(ledger);
		frame = new LedgerFrame(model);
	}

	@Test
	public void layout() {
		assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());
		assertEquals(BorderLayout.class, frame.getLayout().getClass());
		Component[] components = frame.getContentPane().getComponents();
		assertEquals(2, components.length);
		assertEquals(JScrollPane.class, components[0].getClass());
		assertEquals(LedgerTable.class, ((JScrollPane) components[0]).getViewport().getView().getClass());
		assertEquals(AddEntryPanel.class, components[1].getClass());
		AddEntryPanel addPanel = (AddEntryPanel) components[1];
		assertEquals(2, addPanel.getTabCount());
	}

	@Test
	public void menu() {
		assertNotNull(frame.getJMenuBar());
		JMenuBar menuBar = frame.getJMenuBar();
		assertEquals(1, menuBar.getMenuCount());
		JMenu editMenu = menuBar.getMenu(0);
		assertEquals("Edit", editMenu.getText());
		assertEquals(3, editMenu.getItemCount());

		assertEquals("Create Collaborator name", "Create Collaborator", editMenu.getItem(0).getText());
		assertEquals("Create Client name", "Create Client", editMenu.getItem(1).getText());

		assertEquals("Create invoice entries name", "Create Invoice entries", editMenu.getItem(2).getText());
		assertEquals("accelerator key", KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.META_MASK), editMenu.getItem(2).getAccelerator());
	}

	@Test
	public void createCollaboratorShouldOpenDialog() {
		getCreateCollaborator().doClick();
		assertEquals(AddCollaboratorDialog.class, frame.getOwnedWindows()[0].getClass());
		JDialog addCollaboratorDialog = (JDialog) frame.getOwnedWindows()[0];
		assertTrue(addCollaboratorDialog.isVisible());
	}

	@Test
	public void createClientShouldOpenDialog() {
		getCreateClient().doClick();
		assertEquals(AddClientDialog.class, frame.getOwnedWindows()[0].getClass());
		JDialog addClientDialog = (JDialog) frame.getOwnedWindows()[0];
		assertTrue(addClientDialog.isVisible());
	}

	@Test
	public void generateInvoiceEntryShouldUpdateTable() throws InterruptedException {
		getLedgerTable().getSelectionModel().setSelectionInterval(0, 2);
		getGenerateInvoiceEntries().doClick();
		assertEquals(4, getLedgerTable().getRowCount());
	}

	@Test
	public void generateInvoiceEntryShouldUpdateTableWithAllInvoiceEntriesGenerated() throws InterruptedException {
		getLedgerTable().getSelectionModel().setSelectionInterval(0, 5);
		getGenerateInvoiceEntries().doClick();
		assertEquals(2, getLedgerTable().getRowCount());
	}

	private JMenuItem getCreateCollaborator() {
		return getEditMenu().getItem(0);
	}

	private JMenuItem getCreateClient() {
		return getEditMenu().getItem(1);
	}

	private JMenuItem getGenerateInvoiceEntries() {
		return getEditMenu().getItem(2);
	}

	protected JMenu getEditMenu() {
		JMenuBar menuBar = frame.getJMenuBar();
		JMenu editMenu = menuBar.getMenu(0);
		return editMenu;
	}

	private LedgerTable getLedgerTable() {
		return (LedgerTable) ((JScrollPane) frame.getContentPane().getComponents()[0]).getViewport().getView();
	}

}

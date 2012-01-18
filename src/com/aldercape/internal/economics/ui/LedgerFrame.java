package com.aldercape.internal.economics.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.TimeEntry;

public class LedgerFrame extends JFrame {

	private static final long serialVersionUID = -8868637234520419868L;
	private LedgerTable ledgerTable;
	private ApplicationModel model;

	public LedgerFrame(ApplicationModel model) {
		this.model = model;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		ledgerTable = new LedgerTable(model);
		add(new JScrollPane(ledgerTable), BorderLayout.CENTER);
		AddEntryPanel addEntryPanel = new AddEntryPanel();
		addEntryPanel.addType("Invoice Entry", new InvoiceEntryPanel(model));
		addEntryPanel.addType("Time Entry", new TimeEntryPanel(model));

		setJMenuBar(createMenuBar());

		add(addEntryPanel, BorderLayout.SOUTH);
	}

	private JMenuBar createMenuBar() {
		JMenuBar result = new JMenuBar();
		JMenu editMenu = new JMenu("Edit");
		result.add(editMenu);
		editMenu.add(createCollaboratorMenuItem());
		editMenu.add(createClientMenuItem());
		editMenu.add(createInvoiceEntriesMenuItem());
		return result;
	}

	private JMenuItem createClientMenuItem() {
		JMenuItem result = new JMenuItem("Create Client");
		result.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddAbstractEntryPanelDialog dialog = new AddClientDialog(LedgerFrame.this, model);
				dialog.pack();
				dialog.setVisible(true);
			}
		});
		return result;
	}

	protected JMenuItem createInvoiceEntriesMenuItem() {
		JMenuItem jMenuItem = new JMenuItem("Create Invoice entries");
		jMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.META_MASK));
		jMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Set<TimeEntry> timeEntries = ledgerTable.createTimeEntriesFromSelection();
				Set<? extends InvoiceEntry> invoiceEntries = ledgerTable.createInvoiceEntriesFromSelection();
				model.removeEntries(timeEntries);
				model.addEntries(invoiceEntries);
			}
		});
		return jMenuItem;
	}

	protected JMenuItem createCollaboratorMenuItem() {
		JMenuItem createCollaborator = new JMenuItem("Create Collaborator");
		createCollaborator.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddCollaboratorDialog dialog = new AddCollaboratorDialog(LedgerFrame.this, model);
				dialog.pack();
				dialog.setVisible(true);
			}
		});
		return createCollaborator;
	}
}

package com.aldercape.internal.economics.ui;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JDialog;

public abstract class AddAbstractEntryPanelDialog extends JDialog implements StandardButtonTarget {

	private static final long serialVersionUID = 8194709201570506663L;
	private AbstractEntryPanel mainPanel;

	public AddAbstractEntryPanelDialog(Window owner) {
		super(owner);
	}

	public AddAbstractEntryPanelDialog(Window owner, AbstractEntryPanel mainPanel) {
		super(owner);
		this.mainPanel = mainPanel;
		add(mainPanel, BorderLayout.CENTER);
		StandardButtonPanel buttonPanel = new StandardButtonPanel(this);
		add(buttonPanel, BorderLayout.SOUTH);

	}

	@Override
	public void doCancel() {
		dispose();
	}

	@Override
	public void doAdd() {
		mainPanel.addEntry();
	}

}

package com.aldercape.internal.economics.ui;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.aldercape.internal.economics.ApplicationModel;

public abstract class AbstractEntryPanel extends JPanel {

	private static final long serialVersionUID = -7933221838529058950L;
	protected ApplicationModel model;

	public AbstractEntryPanel(ApplicationModel model) {
		setLayout(new MigLayout("wrap 4", "[right]rel[left,grow,fill][right]rel[left,grow,fill]"));
		this.model = model;
	}

	public abstract void addEntry();

}

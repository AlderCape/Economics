package com.aldercape.internal.economics.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.aldercape.internal.economics.ui.base.AbstractEntryPanel;
import com.aldercape.internal.economics.ui.base.StandardButtonPanel;
import com.aldercape.internal.economics.ui.base.StandardButtonTarget;

public class AddEntryPanel extends JPanel implements StandardButtonTarget {

	private static final long serialVersionUID = -5112900796580327651L;
	private JPanel center;
	private JTabbedPane jTabbedPane;

	public AddEntryPanel() {
		setLayout(new BorderLayout());

		center = new JPanel(new CardLayout());
		add(center, BorderLayout.CENTER);
		add(new StandardButtonPanel(this), BorderLayout.SOUTH);

		JPanel typePanel = new JPanel();
		jTabbedPane = new JTabbedPane();
		typePanel.add(jTabbedPane);
		add(jTabbedPane, BorderLayout.CENTER);
	}

	@Override
	public void doCancel() {
	}

	protected void addEntry() {
		((AbstractEntryPanel) jTabbedPane.getSelectedComponent()).addEntry();
	}

	public void addType(String name, AbstractEntryPanel mainPanel) {
		jTabbedPane.addTab(name, mainPanel);
		jTabbedPane.setSelectedIndex(0);
	}

	public int getTabCount() {
		return jTabbedPane.getTabCount();
	}

	@Override
	public void doAdd() {
		addEntry();
	}

}

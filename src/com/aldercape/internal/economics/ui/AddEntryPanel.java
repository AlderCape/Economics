package com.aldercape.internal.economics.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class AddEntryPanel extends JPanel {

	private static final long serialVersionUID = -5112900796580327651L;
	private JPanel center;
	private JTabbedPane jTabbedPane;

	public AddEntryPanel() {
		setLayout(new BorderLayout());

		center = new JPanel(new CardLayout());
		add(center, BorderLayout.CENTER);
		JPanel btnPanel = new JPanel();
		btnPanel.add(new JButton("Cancel"));
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addEntry();
			}
		});
		btnPanel.add(addButton);
		add(btnPanel, BorderLayout.SOUTH);

		JPanel typePanel = new JPanel();
		jTabbedPane = new JTabbedPane();
		typePanel.add(jTabbedPane);
		add(jTabbedPane, BorderLayout.CENTER);
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

}

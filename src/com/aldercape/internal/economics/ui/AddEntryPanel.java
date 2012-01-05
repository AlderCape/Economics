package com.aldercape.internal.economics.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AddEntryPanel extends JPanel {

	private static final long serialVersionUID = -5112900796580327651L;

	public AddEntryPanel(final AbstractEntryPanel mainPanel) {
		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
		JPanel btnPanel = new JPanel();
		btnPanel.add(new JButton("Cancel"));
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.addEntry();
			}
		});
		btnPanel.add(addButton);
		add(btnPanel, BorderLayout.SOUTH);
	}
}

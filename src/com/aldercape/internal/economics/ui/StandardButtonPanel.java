package com.aldercape.internal.economics.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StandardButtonPanel extends JPanel {

	private static final long serialVersionUID = 7073706963807809583L;
	private JButton addButton;
	private JButton cancelButton;

	public StandardButtonPanel(final StandardButtonTarget target) {
		cancelButton = new JButton("Cancel");
		add(cancelButton);
		addButton = new JButton("Add");
		add(addButton);
		addAddButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				target.doAdd();
			}
		});
		addCancelActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				target.doCancel();
			}
		});

	}

	public void addAddButtonActionListener(ActionListener actionListener) {
		addButton.addActionListener(actionListener);
	}

	public void addCancelActionListener(ActionListener actionListener) {
		cancelButton.addActionListener(actionListener);
	}
}

package com.aldercape.internal.economics.ui;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.aldercape.internal.economics.ApplicationModel;

public class AddClientDialog extends JDialog {

	public AddClientDialog(Window owner, ApplicationModel model) {
		super(owner);
		clientPanel = new ClientPanel(model);
		add(clientPanel, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddClientDialog.this.dispose();
			}
		});
		buttonPanel.add(cancelButton);
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clientPanel.addEntry();
			}
		});
		buttonPanel.add(addButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private static final long serialVersionUID = 2927803598709113263L;
	private ClientPanel clientPanel;

}

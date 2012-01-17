package com.aldercape.internal.economics.ui;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.aldercape.internal.economics.ApplicationModel;

public class AddCollaboratorDialog extends JDialog {

	private static final long serialVersionUID = -5612647211154416250L;
	private CollaboratorPanel collaboratorPanel;

	public AddCollaboratorDialog(Window owner, ApplicationModel model) {
		super(owner);
		collaboratorPanel = new CollaboratorPanel(model);
		add(collaboratorPanel, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddCollaboratorDialog.this.dispose();
			}
		});
		buttonPanel.add(cancelButton);
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				collaboratorPanel.addEntry();
			}
		});
		buttonPanel.add(addButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}
}

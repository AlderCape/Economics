package com.aldercape.internal.economics.ui;

import java.awt.Window;

import com.aldercape.internal.economics.ApplicationModel;

public class AddCollaboratorDialog extends AddAbstractEntryPanelDialog {

	private static final long serialVersionUID = -5612647211154416250L;

	public AddCollaboratorDialog(Window owner, ApplicationModel model) {
		super(owner, new CollaboratorPanel(model));
	}
}

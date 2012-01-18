package com.aldercape.internal.economics.ui.collaborator;

import java.awt.Window;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.ui.base.AddAbstractEntryPanelDialog;

public class AddCollaboratorDialog extends AddAbstractEntryPanelDialog {

	private static final long serialVersionUID = -5612647211154416250L;

	public AddCollaboratorDialog(Window owner, ApplicationModel model) {
		super(owner, new CollaboratorPanel(model));
	}
}

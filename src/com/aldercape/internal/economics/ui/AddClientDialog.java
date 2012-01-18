package com.aldercape.internal.economics.ui;

import java.awt.Window;

import com.aldercape.internal.economics.ApplicationModel;

public class AddClientDialog extends AddAbstractEntryPanelDialog {

	private static final long serialVersionUID = 6069424768031297357L;

	public AddClientDialog(Window owner, ApplicationModel model) {
		super(owner, new ClientPanel(model));
	}
}

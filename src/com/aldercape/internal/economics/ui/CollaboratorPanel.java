package com.aldercape.internal.economics.ui;

import javax.swing.JLabel;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Collaborator.CollaboratorRenderTarget;

public class CollaboratorPanel extends AbstractEntryPanel implements CollaboratorRenderTarget {

	public CollaboratorPanel(ApplicationModel model) {
		super(model);
		add(new JLabel("First name"));
		firstName = new StringTextField();
		add(firstName);
		add(new JLabel("Last name"));
		add(new StringTextField());
	}

	private static final long serialVersionUID = -2723575180585263347L;
	private StringTextField firstName;

	@Override
	public void addEntry() {
		model.getCollaboratorRepository().add(createCollaborator());
	}

	private Collaborator createCollaborator() {
		return new Collaborator(firstName.getValue());
	}

	public void setEntry(Collaborator collaborator) {
		collaborator.render(this);
	}

	@Override
	public void renderName(Collaborator name) {
		firstName.setValue(name.name());
	}

}

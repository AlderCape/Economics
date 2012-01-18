package com.aldercape.internal.economics.ui.collaborator;

import javax.swing.JLabel;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Collaborator.CollaboratorRenderTarget;
import com.aldercape.internal.economics.ui.base.AbstractEntryPanel;
import com.aldercape.internal.economics.ui.base.StringTextField;

public class CollaboratorPanel extends AbstractEntryPanel implements CollaboratorRenderTarget {

	public CollaboratorPanel(ApplicationModel model) {
		super(model);
		add(new JLabel("First name"));
		firstName = new StringTextField();
		add(firstName);
		add(new JLabel("Last name"));
		lastname = new StringTextField();
		add(lastname);
	}

	private static final long serialVersionUID = -2723575180585263347L;
	private StringTextField firstName;
	private StringTextField lastname;

	@Override
	public void addEntry() {
		model.getCollaboratorRepository().add(createCollaborator());
	}

	private Collaborator createCollaborator() {
		return new Collaborator(firstName.getValue(), lastname.getValue());
	}

	public void setEntry(Collaborator collaborator) {
		collaborator.render(this);
	}

	@Override
	public void renderFullName(Collaborator name) {
	}

	@Override
	public void renderFirstName(String name) {
		firstName.setValue(name);
	}

	@Override
	public void renderLastName(String name) {
		lastname.setValue(name);
	}

}
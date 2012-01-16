package com.aldercape.internal.economics.ui;

import javax.swing.JComboBox;

import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Collaborator.CollaboratorRenderTarget;
import com.aldercape.internal.economics.model.CollaboratorRepository;

public class CollaboratorField extends JComboBox<Collaborator> implements CollaboratorRenderTarget {

	private static final long serialVersionUID = -3265905259908228386L;
	private CollaboratorRepositoryComboBoxModel model;

	public CollaboratorField(CollaboratorRepository collaboratorRepository) {
		model = new CollaboratorRepositoryComboBoxModel(collaboratorRepository);
		setModel(model);
	}

	protected Collaborator createDomainObject() {
		return model.getSelectedCollaborator();
	}

	@Override
	public void renderName(Collaborator name) {
		model.setSelectedItem(name);
	}
}

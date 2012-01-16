package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.BaseRepository.Listener;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.CollaboratorRepository;

public class CollaboratorRepositoryComboBoxModel extends AbstractRepositoryComboBoxModel<Collaborator> implements Listener {

	private static final long serialVersionUID = 3137329985124611882L;

	public CollaboratorRepositoryComboBoxModel(CollaboratorRepository repository) {
		super(repository);
	}

	@Override
	protected void assertClass(Object anItem) {
		if (!(anItem instanceof Collaborator)) {
			throw new IllegalArgumentException("Only colaborators can be stored got item of type " + anItem.getClass());
		}
	}

	public Collaborator getSelectedCollaborator() {
		return selectedItem;
	}

}

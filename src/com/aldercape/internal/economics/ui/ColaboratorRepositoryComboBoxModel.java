package com.aldercape.internal.economics.ui;

import com.aldercape.internal.economics.model.BaseRepository.Listener;
import com.aldercape.internal.economics.model.Colaborator;
import com.aldercape.internal.economics.model.ColaboratorRepository;

public class ColaboratorRepositoryComboBoxModel extends AbstractRepositoryComboBoxModel<Colaborator> implements Listener {

	private static final long serialVersionUID = 3137329985124611882L;

	public ColaboratorRepositoryComboBoxModel(ColaboratorRepository repository) {
		super(repository);
	}

	@Override
	protected void assertClass(Object anItem) {
		if (!(anItem instanceof Colaborator)) {
			throw new IllegalArgumentException("Only colaborators can be stored got item of type " + anItem.getClass());
		}
	}

	public Colaborator getSelectedColaborator() {
		return selectedItem;
	}

}

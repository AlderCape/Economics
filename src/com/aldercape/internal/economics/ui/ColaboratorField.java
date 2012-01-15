package com.aldercape.internal.economics.ui;

import javax.swing.JComboBox;

import com.aldercape.internal.economics.model.Colaborator;
import com.aldercape.internal.economics.model.Colaborator.ColaboratorRenderTarget;
import com.aldercape.internal.economics.model.ColaboratorRepository;

public class ColaboratorField extends JComboBox<Colaborator> implements ColaboratorRenderTarget {

	private static final long serialVersionUID = -3265905259908228386L;
	private ColaboratorRepositoryComboBoxModel model;

	public ColaboratorField(ColaboratorRepository colaboratorRepository) {
		model = new ColaboratorRepositoryComboBoxModel(colaboratorRepository);
		setModel(model);
	}

	protected Colaborator createDomainObject() {
		return model.getSelectedColaborator();
	}

	@Override
	public void renderName(Colaborator name) {
		model.setSelectedItem(name);
	}
}

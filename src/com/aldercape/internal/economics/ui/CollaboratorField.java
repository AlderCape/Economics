package com.aldercape.internal.economics.ui;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Collaborator.CollaboratorRenderTarget;
import com.aldercape.internal.economics.model.CollaboratorRepository;

public class CollaboratorField extends JComboBox<Collaborator> implements CollaboratorRenderTarget {

	private static final long serialVersionUID = -3265905259908228386L;
	private CollaboratorRepositoryComboBoxModel model;

	public CollaboratorField(CollaboratorRepository collaboratorRepository) {
		model = new CollaboratorRepositoryComboBoxModel(collaboratorRepository);
		setModel(model);
		setRenderer();
	}

	private void setRenderer() {
		setRenderer(new ListCellRenderer<Collaborator>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends Collaborator> list, Collaborator value, int index, boolean isSelected, boolean cellHasFocus) {
				return new JLabel(value == null ? "" : value.fullname());
			}
		});
	}

	protected Collaborator createDomainObject() {
		return model.getSelectedCollaborator();
	}

	@Override
	public void renderFullName(Collaborator name) {
		model.setSelectedItem(name);
	}

	@Override
	public void renderFirstName(String name) {
	}

	@Override
	public void renderLastName(String name) {
	}

	@Override
	public void renderEmail(String email) {
	}
}

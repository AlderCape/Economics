package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.ClientRepository;

public class AddClientDialogTest {

	private AddClientDialog addClientDialog;
	private ClientRepository repository;

	@Before
	public void setUp() {
		ApplicationModel model = new ApplicationModel(null);
		repository = model.getClientRepository();
		addClientDialog = new AddClientDialog(null, model);
	}

	@Test
	public void layout() {
		assertEquals(BorderLayout.class, addClientDialog.getLayout().getClass());
		assertEquals(2, addClientDialog.getContentPane().getComponents().length);

		assertEquals(ClientPanel.class, addClientDialog.getContentPane().getComponent(0).getClass());
		assertEquals(JPanel.class, addClientDialog.getContentPane().getComponent(1).getClass());

		JPanel southComponent = getSouthComponent();
		assertEquals("# of South components", 2, southComponent.getComponents().length);
		assertEquals(JButton.class, southComponent.getComponent(0).getClass());
		JButton cancelButton = cancelButton();
		assertEquals("Cancel", cancelButton.getText());
		assertEquals(JButton.class, southComponent.getComponent(1).getClass());
		assertEquals("Add", addButton().getText());
	}

	@Test
	public void addButtonShouldAddCollaboratorToRepository() {
		assertEquals(0, repository.getAll().size());
		addButton().doClick();
		assertEquals(1, repository.getAll().size());
	}

	@Test
	public void cancelButtonShouldDisposeDialog() {
		addClientDialog.setVisible(true);
		cancelButton().doClick();
		assertFalse(addClientDialog.isDisplayable());
	}

	protected JButton cancelButton() {
		return (JButton) getSouthComponent().getComponent(0);
	}

	protected JButton addButton() {
		return (JButton) getSouthComponent().getComponent(1);
	}

	protected JPanel getSouthComponent() {
		return (JPanel) getLayout().getLayoutComponent(BorderLayout.SOUTH);
	}

	protected BorderLayout getLayout() {
		return (BorderLayout) addClientDialog.getContentPane().getLayout();
	}

}

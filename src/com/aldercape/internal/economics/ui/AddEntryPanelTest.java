package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

public class AddEntryPanelTest {

	private AbstractEntryPanel mainPanel;
	private AddEntryPanel panel;
	protected boolean addEntryCalled = false;

	@SuppressWarnings("serial")
	@Before
	public void setUp() {
		mainPanel = new AbstractEntryPanel() {
			@Override
			public void addEntry() {
				addEntryCalled = true;
			}
		};
		panel = new AddEntryPanel(mainPanel);
	}

	@Test
	public void layout() {
		assertEquals(BorderLayout.class, panel.getLayout().getClass());
		BorderLayout layout = (BorderLayout) panel.getLayout();
		assertEquals("passed in panel should be in center", mainPanel, layout.getLayoutComponent(BorderLayout.CENTER));
		assertEquals("Sout component class", JPanel.class, layout.getLayoutComponent(BorderLayout.SOUTH).getClass());
		JPanel southComponent = (JPanel) layout.getLayoutComponent(BorderLayout.SOUTH);
		assertEquals("# of South components", 2, southComponent.getComponents().length);
		assertEquals(JButton.class, southComponent.getComponent(0).getClass());
		JButton cancelButton = (JButton) southComponent.getComponent(0);
		assertEquals("Cancel", cancelButton.getText());
		assertEquals(JButton.class, southComponent.getComponent(1).getClass());
		JButton addButton = getAddButton();
		assertEquals("Add", addButton.getText());
	}

	@Test
	public void addButtonShouldCallMainPanelsAddEntryMethod() {
		assertFalse(addEntryCalled);
		getAddButton().doClick();
		assertTrue(addEntryCalled);
	}

	private JButton getAddButton() {
		return (JButton) ((JPanel) ((BorderLayout) panel.getLayout()).getLayoutComponent(BorderLayout.SOUTH)).getComponent(1);
	}
}

package com.aldercape.internal.economics.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.junit.Before;
import org.junit.Test;

public class AddEntryPanelTest {

	private AbstractEntryPanel mainPanel;
	private AddEntryPanel panel;
	protected boolean addEntryCalled = false;

	@SuppressWarnings("serial")
	@Before
	public void setUp() {
		mainPanel = new AbstractEntryPanel(null) {
			@Override
			public void addEntry() {
				addEntryCalled = true;
			}
		};
		panel = new AddEntryPanel();
	}

	@Test
	public void layout() {
		assertEquals(BorderLayout.class, panel.getLayout().getClass());
		BorderLayout layout = getLayout();
		assertEquals("Tabbed panel in center on construction", JTabbedPane.class, layout.getLayoutComponent(BorderLayout.CENTER).getClass());

		assertEquals("South component class", StandardButtonPanel.class, layout.getLayoutComponent(BorderLayout.SOUTH).getClass());
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
	public void whenOnlyOneTypeItShouldBeDisplayedInCenter() {
		panel.addType("Test type", mainPanel);
		assertEquals(mainPanel, getSelectedTabComponent());
		// assertEquals("", getTypeCombo().getRenderer().);
	}

	private Component getSelectedTabComponent() {
		JTabbedPane layoutComponent = (JTabbedPane) getLayout().getLayoutComponent(BorderLayout.CENTER);
		return layoutComponent.getSelectedComponent();
	}

	@Test
	public void whenMoreThenOneTypeTheFirstShouldBeDisplayedInCenter() {
		panel.addType("Test type", mainPanel);
		panel.addType("Test again type", new AbstractEntryPanel(null) {
			private static final long serialVersionUID = 1L;

			@Override
			public void addEntry() {
			}
		});
		assertEquals(mainPanel, getSelectedTabComponent());
	}

	@Test
	public void addButtonShouldCallMainPanelsAddEntryMethod() {
		panel.addType("Test type", mainPanel);
		panel.addType("Test again type", new AbstractEntryPanel(null) {
			private static final long serialVersionUID = 1L;

			@Override
			public void addEntry() {
			}
		});

		assertFalse(addEntryCalled);
		getAddButton().doClick();
		assertTrue(addEntryCalled);
	}

	private JButton getAddButton() {
		return (JButton) getComponent(BorderLayout.SOUTH).getComponent(1);
	}

	private JPanel getComponent(String position) {
		return (JPanel) getLayout().getLayoutComponent(position);
	}

	private BorderLayout getLayout() {
		return (BorderLayout) panel.getLayout();
	}
}

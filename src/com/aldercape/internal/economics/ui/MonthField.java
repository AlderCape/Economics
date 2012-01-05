package com.aldercape.internal.economics.ui;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.aldercape.internal.economics.model.Month;
import com.aldercape.internal.economics.model.MonthLiteral;
import com.aldercape.internal.economics.model.RenderTarget;

public class MonthField extends JPanel implements RenderTarget {

	private static final long serialVersionUID = 5322903221489553330L;
	private JComboBox monthComboBox;
	private JTextField yearTextField;

	public MonthField(Month month) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		createComponents();
		render(month);
	}

	private void createComponents() {
		createMonthComboBox();
		createYearTextField();
	}

	private void render(Month month) {
		month.render(this);
	}

	private void createYearTextField() {
		yearTextField = new JTextField();
		add(yearTextField);
	}

	private void createMonthComboBox() {
		monthComboBox = new JComboBox(MonthLiteral.values());
		add(monthComboBox);
	}

	public String getText() {
		return yearTextField.getText();
	}

	public Month getMonth() {
		return Month.createFrom((MonthLiteral) getSelectedItem(), getText());
	}

	@Override
	public void setSelectedItem(Object item) {
		monthComboBox.setSelectedItem(item);
	}

	public Object getSelectedItem() {
		return monthComboBox.getSelectedItem();
	}

	@Override
	public void setDisplayText(String year) {
		yearTextField.setText(year);
	}
}

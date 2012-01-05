package com.aldercape.internal.economics.ui;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.MonthLiteral;
import com.aldercape.internal.economics.model.RenderTarget;

public class DayTextField extends JTextField implements RenderTarget {

	private static final long serialVersionUID = 1L;
	private JComboBox month;
	private JTextField dayField;
	private JTextField year;

	public DayTextField(Day day) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		month = new JComboBox(MonthLiteral.values());
		add(month);
		dayField = new JTextField();
		add(dayField);
		year = new JTextField();
		add(year);
		day.month().render(new RenderTarget() {

			@Override
			public void setDisplayText(String text) {
				year.setText(text);
			}

			@Override
			public void setSelectedItem(Object item) {
				month.setSelectedItem(item);
			}
		});
		day.render(new RenderTarget() {

			@Override
			public void setDisplayText(String text) {
				dayField.setText(text);
			}

			@Override
			public void setSelectedItem(Object item) {
				// TODO Auto-generated method stub

			}
		});
	}

	public Day createDomainObject() {
		return Day.createFrom((MonthLiteral) month.getSelectedItem(), dayField.getText(), year.getText());
	}

	public MonthLiteral getSelectedItem() {
		// TODO Auto-generated method stub
		return null;
	}

	public Day getDay() {
		return createDomainObject();
	}

	@Override
	public void setDisplayText(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSelectedItem(Object item) {
		// TODO Auto-generated method stub

	}

}

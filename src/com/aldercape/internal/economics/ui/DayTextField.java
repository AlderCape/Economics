package com.aldercape.internal.economics.ui;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Day.DayRenderTarget;
import com.aldercape.internal.economics.model.MonthLiteral;

public class DayTextField extends JPanel implements DayRenderTarget {

	private static final long serialVersionUID = -4011989890153583631L;
	private JComboBox<MonthLiteral> month;
	private IntTextField dayField;
	private IntTextField year;

	public DayTextField(Day day) {
		setLayout();
		createComponents();
		render(day);
	}

	private void setLayout() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}

	private void render(Day day) {
		day.render(this);
	}

	private void createComponents() {
		month = new JComboBox<MonthLiteral>(MonthLiteral.values());
		add(month);
		dayField = new IntTextField();
		add(dayField);
		year = new IntTextField();
		add(year);
	}

	public Day createDomainObject() {
		return Day.createFrom((MonthLiteral) month.getSelectedItem(), dayField.getValue(), year.getValue());
	}

	public Day getDay() {
		return createDomainObject();
	}

	@Override
	public void renderDay(int day) {
		this.dayField.setValue(day);
	}

	@Override
	public void renderMonth(MonthLiteral month) {
		this.month.setSelectedItem(month);
	}

	@Override
	public void renderYear(int year) {
		this.year.setValue(year);
	}

}

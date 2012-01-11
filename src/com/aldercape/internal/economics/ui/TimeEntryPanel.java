package com.aldercape.internal.economics.ui;

import javax.swing.JLabel;

import com.aldercape.internal.economics.ApplicationModel;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.TimeEntry;

public class TimeEntryPanel extends AbstractEntryPanel {

	private static final long serialVersionUID = 1L;
	private UnitTextField units;
	private EuroTextField rate;
	private ColaboratorTextField person;
	private ClientTextField client;
	private DayTextField day;

	public TimeEntryPanel(ApplicationModel model) {
		super(model);
		add(new JLabel("Unit"));
		units = new UnitTextField();
		add(units);

		add(new JLabel("Rate"));
		rate = new EuroTextField(new Euro(0));
		add(rate);

		add(new JLabel("Person"));
		person = new ColaboratorTextField();
		add(person);

		add(new JLabel("Client"));
		client = new ClientTextField();
		add(client);

		add(new JLabel("Day"));
		day = new DayTextField(Day.january(1, 2011));
		add(day);
	}

	public void setEntry(TimeEntry populatWith) {
		populatWith.units().render(units);
		populatWith.rate().render(rate);
		populatWith.colaborator().render(person);
		populatWith.client().render(client);
		populatWith.day().render(day);
	}

	@Override
	public void addEntry() {
		model.addEntry(new TimeEntry(units.createDomainObject(), rate.createDomainObject(), person.createDomainObject(), client.createDomainObject(), day.createDomainObject()));
	}

}

package com.aldercape.internal.economics.ui;

import javax.swing.JLabel;

import com.aldercape.internal.economics.model.ApplicationModel;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.InvoiceEntry;

public class InvoiceEntryPanel extends AbstractEntryPanel {

	private static final long serialVersionUID = 4465473290587827631L;
	private ApplicationModel applicationModel;
	private UnitTextField units;
	private EuroTextField rate;
	private ColaboratorTextField person;
	private ClientTextField client;
	private DayTextField bookkeepingMonth;

	public InvoiceEntryPanel(ApplicationModel ledger) {
		super(ledger);
		this.applicationModel = ledger;

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

		add(new JLabel("Bookkeeping month"));
		bookkeepingMonth = new DayTextField(Day.january(1, 2012));
		add(bookkeepingMonth);
	}

	@Override
	public void addEntry() {
		applicationModel.addEntry(new InvoiceEntry(units.createDomainObject(), rate.createDomainObject(), person.createDomainObject(), client.createDomainObject(), bookkeepingMonth.createDomainObject()));
	}

	public void setEntry(InvoiceEntry populatWith) {
		populatWith.units().render(units);
		populatWith.rate().render(rate);
		populatWith.colaborator().render(person);
		populatWith.client().render(client);
		populatWith.issueDate().render(bookkeepingMonth);
	}
}

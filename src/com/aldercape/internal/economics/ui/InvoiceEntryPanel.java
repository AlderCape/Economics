package com.aldercape.internal.economics.ui;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import com.aldercape.internal.economics.model.ApplicationModel;
import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Colaborator;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.InvoiceEntry;
import com.aldercape.internal.economics.model.Month;
import com.aldercape.internal.economics.model.Unit;

public class InvoiceEntryPanel extends AbstractEntryPanel {

	private static final long serialVersionUID = 4465473290587827631L;
	private ApplicationModel ledger;
	private TextFieldRenderTarget<Unit> units;
	private TextFieldRenderTarget<Euro> rate;
	private TextFieldRenderTarget<Colaborator> person;
	private TextFieldRenderTarget<Client> client;
	private MonthField bookkeepingMonth;
	private MonthField cashflowMonth;

	public InvoiceEntryPanel(ApplicationModel ledger) {
		this.ledger = ledger;
		setLayout(new MigLayout("wrap 4", "[right]rel[left,grow,fill][right]rel[left,grow,fill]"));

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
		bookkeepingMonth = new MonthField(Month.december(2011));
		add(bookkeepingMonth);
		add(new JLabel("Cashflow month"));
		cashflowMonth = new MonthField(Month.february(2012));
		add(cashflowMonth);
	}

	@Override
	public void addEntry() {
		ledger.addEntry(new InvoiceEntry(units.createDomainObject(), rate.createDomainObject(), person.createDomainObject(), client.createDomainObject(), bookkeepingMonth.getMonth(), cashflowMonth.getMonth()));
	}

	public void setEntry(InvoiceEntry populatWith) {
		populatWith.units().render(units);
		populatWith.rate().render(rate);
		populatWith.colaborator().render(person);
		populatWith.client().render(client);
		populatWith.bookkeepingMonth().render(bookkeepingMonth);
		populatWith.cashflowMonth().render(cashflowMonth);
	}
}

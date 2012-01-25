package com.aldercape.internal.economics.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InvoiceEntryGroupingRule {

	private Rule<Entry<Day>> timePoint = new MonthRule();
	private Rule<Entry<Day>> rate = new RateRule();
	private Rule<Entry<Day>> collaborator = new CollaboratorRule();
	private Rule<Entry<Day>> client = new ClientRule();
	private List<Rule<Entry<Day>>> rules = new ArrayList<Rule<Entry<Day>>>();

	public InvoiceEntryGroupingRule() {
		rules.add(collaborator);
		rules.add(rate);
		rules.add(timePoint);
		rules.add(client);

	}

	public EntryCriteria<Day> getCriteria(Entry<Day> example) {
		EntryCriteria<Day> criteria = null;
		for (Rule<Entry<Day>> rule : rules) {
			if (criteria == null) {
				criteria = rule.getCriteria(example);
			} else {
				criteria = criteria.and(rule.getCriteria(example));
			}
		}
		return criteria;
	}

	public Comparator<TimeEntry> getComparator() {
		return new Comparator<TimeEntry>() {
			@Override
			public int compare(TimeEntry o1, TimeEntry o2) {
				for (Rule<Entry<Day>> rule : rules) {
					if (rule.getComparator().compare(o1, o2) != 0) {
						return rule.getComparator().compare(o1, o2);
					}
				}
				if (o1.hashCode() > o2.hashCode()) {
					return -1;
				}
				if (o1.hashCode() < o2.hashCode()) {
					return 1;
				}
				return 0;

			}
		};
	}
}
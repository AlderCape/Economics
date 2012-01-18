package com.aldercape.internal.economics.criteria;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.SimpleInvoiceEntry;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class FilterCriteriaTest {

	@Test
	public void and() {
		__TestObjectMother objectMother = new __TestObjectMother();
		Collaborator me = objectMother.me();
		Client client = objectMother.myCompany();
		CollaboratorCriteria<Day> collaboratorCriteria = new CollaboratorCriteria<Day>(me);
		ClientCriteria<Day> clientCriteria = new ClientCriteria<Day>(client);
		SimpleInvoiceEntry matchingEntry = new SimpleInvoiceEntry(Unit.days(1), new Euro(300), objectMother.me(), objectMother.myCompany(), Day.january(1, 2012));
		assertTrue(collaboratorCriteria.and(clientCriteria).matches(matchingEntry));
		assertTrue(clientCriteria.and(collaboratorCriteria).matches(matchingEntry));
	}
}

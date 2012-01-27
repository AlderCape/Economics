package com.aldercape.internal.economics.google.calendar;

import static com.aldercape.internal.economics.model.CustomModelAsserts.assertClientEquals;
import static com.aldercape.internal.economics.model.CustomModelAsserts.assertCollaboratorEquals;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.aldercape.internal.economics.model.Client;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.persistence.InMemoryClientRepository;
import com.aldercape.internal.economics.persistence.InMemoryCollaboratorRepository;
import com.aldercape.internal.economics.ui.__TestObjectMother;
import com.google.api.client.util.ArrayMap;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

public class CalendarEntryTest {

	private ClientRepositorySpy clientRepository;
	private CollaboratorRepositorySpy collaboratorRepository;

	private class CollaboratorRepositorySpy extends InMemoryCollaboratorRepository {
		public String searchedEmail;

		@Override
		public Collaborator findByEmail(String email) {
			searchedEmail = email;
			return new __TestObjectMother().me();
		}

	}

	private class ClientRepositorySpy extends InMemoryClientRepository {
		public String searchedName;

		@Override
		public Client getByName(String name) {
			searchedName = name;
			return new __TestObjectMother().otherCompany();
		}

	}

	@Before
	public void setUp() {
		collaboratorRepository = new CollaboratorRepositorySpy();
		clientRepository = new ClientRepositorySpy();
	}

	@Test
	public void wholeDayEventShouldHaveUnitOfOneDay() throws Exception {
		Event calendar = new Event();
		setWholeDay(calendar);
		setUnknownKeys(calendar);
		setAttendees(calendar);

		CalendarEntry calendarEntry = new CalendarEntry(calendar, collaboratorRepository, clientRepository);

		assertEquals(Unit.days(1), calendarEntry.units());
		assertEquals(Day.january(12, 2012), calendarEntry.getTimePoint());
	}

	@Test
	public void eightHourEventShouldHaveUnitOfOneDay() throws Exception {
		Event calendar = new Event();
		setUnknownKeys(calendar);
		setAttendees(calendar);

		DateTime startDateTime = new DateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2012-01-12 09:00"));
		calendar.setStart(new EventDateTime().setDateTime(startDateTime));
		DateTime endDateTime = new DateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2012-01-13 17:00"));
		calendar.setEnd(new EventDateTime().setDateTime(endDateTime));
		CalendarEntry calendarEntry = new CalendarEntry(calendar, collaboratorRepository, clientRepository);

		assertEquals(Unit.days(1), calendarEntry.units());
		assertEquals(Day.january(12, 2012), calendarEntry.getTimePoint());
	}

	@Test
	public void sixteenHourEventShouldHaveUnitOfTwoDay() throws Exception {
		Event calendar = new Event();
		setUnknownKeys(calendar);
		setAttendees(calendar);

		DateTime startDateTime = new DateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2012-01-12 07:00"));
		calendar.setStart(new EventDateTime().setDateTime(startDateTime));
		DateTime endDateTime = new DateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2012-01-13 23:00"));
		calendar.setEnd(new EventDateTime().setDateTime(endDateTime));
		CalendarEntry calendarEntry = new CalendarEntry(calendar, collaboratorRepository, clientRepository);

		assertEquals(Unit.days(2), calendarEntry.units());
		assertEquals(Day.january(12, 2012), calendarEntry.getTimePoint());
	}

	@Test
	public void collaboratorShouldBeFromAttendeeList() throws ParseException {

		Event calendar = new Event();
		setWholeDay(calendar);
		setUnknownKeys(calendar);
		setAttendees(calendar);

		CalendarEntry calendarEntry = new CalendarEntry(calendar, collaboratorRepository, clientRepository);

		assertCollaboratorEquals(new __TestObjectMother().me(), calendarEntry.collaborator());
		assertEquals("me@mycompany.com", collaboratorRepository.searchedEmail);
	}

	@Test
	public void clientShouldBeFromUnkownKeys() throws Exception {
		Event calendar = new Event();
		setWholeDay(calendar);
		setUnknownKeys(calendar);
		setAttendees(calendar);

		CalendarEntry calendarEntry = new CalendarEntry(calendar, collaboratorRepository, clientRepository);

		assertClientEquals(new __TestObjectMother().otherCompany(), calendarEntry.client());
		assertEquals("Other Company", clientRepository.searchedName);
	}

	@Test
	public void rateShouldBeFromUnkownKeys() throws Exception {
		Event calendar = new Event();
		setWholeDay(calendar);
		setUnknownKeys(calendar);
		setAttendees(calendar);

		CalendarEntry calendarEntry = new CalendarEntry(calendar, collaboratorRepository, clientRepository);

		assertEquals(Rate.daily(new Euro(100)), calendarEntry.rate());
		assertEquals(new Euro(100), calendarEntry.amount());
		assertEquals(new Euro(21), calendarEntry.vat());
	}

	private void setUnknownKeys(Event calendar) {
		ArrayMap<String, Object> unkownKeys = new ArrayMap<String, Object>();
		unkownKeys.put("rate", "100");
		unkownKeys.put("client", new __TestObjectMother().otherCompany().name());

		calendar.setUnknownKeys(unkownKeys);
	}

	private void setWholeDay(Event calendar) throws ParseException {
		DateTime startDateTime = new DateTime(new SimpleDateFormat("yyyy-MM-dd").parse("2012-01-12"));
		calendar.setStart(new EventDateTime().setDateTime(startDateTime));
		DateTime endDateTime = new DateTime(new SimpleDateFormat("yyyy-MM-dd").parse("2012-01-13"));
		calendar.setEnd(new EventDateTime().setDateTime(endDateTime));
	}

	private void setAttendees(Event calendar) {
		List<EventAttendee> attendees = new ArrayList<EventAttendee>();
		EventAttendee onlyAttendee = new EventAttendee();
		onlyAttendee.setEmail("me@mycompany.com");
		attendees.add(onlyAttendee);
		calendar.setAttendees(attendees);
	}

}

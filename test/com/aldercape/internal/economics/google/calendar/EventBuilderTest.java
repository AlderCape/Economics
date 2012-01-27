package com.aldercape.internal.economics.google.calendar;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.model.TimeEntry;
import com.aldercape.internal.economics.model.Unit;
import com.aldercape.internal.economics.ui.__TestObjectMother;
import com.google.api.services.calendar.model.Event;

public class EventBuilderTest {

	@Test
	public void toNewCalendarEntry() {
		TimeEntry entry = new TimeEntry(Unit.days(1), Rate.daily(100), new __TestObjectMother().me(), new __TestObjectMother().otherCompany(), Day.january(12, 2012));
		EventBuilder builder = new EventBuilder();
		Event event = builder.convert(entry);
		Date startDate = new Date(event.getStart().getDateTime().getValue());
		assertEquals("2012-01-12 00:00", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(startDate));
		Date endDate = new Date(event.getEnd().getDateTime().getValue());
		assertEquals("2012-01-13 00:00", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(endDate));

		assertEquals(1, event.getAttendees().size());
		assertEquals("me@mycompany.com", event.getAttendees().get(0).getEmail());
		assertEquals("100", event.getUnknownKeys().get("rate"));
		assertEquals(new __TestObjectMother().otherCompany().name(), event.getUnknownKeys().get("client"));
	}
}

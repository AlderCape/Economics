package com.aldercape.internal.economics.google.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.google.api.client.util.ArrayMap;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

public class EventBuilder {

	public Event convert(Entry<Day> entry) {
		try {
			Event result = new Event();
			result.setStart(createStartTime(entry));
			result.setEnd(createEndTime(entry));
			result.setAttendees(createAttendees(entry));
			result.setUnknownKeys(createUnknownKeys(entry));
			return result;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private ArrayMap<String, Object> createUnknownKeys(Entry<Day> entry) {
		ArrayMap<String, Object> unknownKeys = new ArrayMap<>();
		unknownKeys.add("rate", createRate(entry));
		unknownKeys.add("client", createClient(entry));
		return unknownKeys;
	}

	private String createClient(Entry<Day> entry) {
		return entry.client().name();
	}

	private String createRate(Entry<Day> entry) {
		return entry.rate().costPerDay().simpleValue();
	}

	private EventDateTime createEndTime(Entry<Day> entry) throws ParseException {
		return createDateTime(entry.getTimePoint().daysAfter(1));
	}

	private EventDateTime createStartTime(Entry<Day> entry) throws ParseException {
		return createDateTime(entry.getTimePoint());
	}

	private List<EventAttendee> createAttendees(Entry<Day> entry) {
		List<EventAttendee> attendees = new ArrayList<EventAttendee>();
		EventAttendee attendee = new EventAttendee();
		attendee.setEmail(entry.collaborator().email());
		attendees.add(attendee);
		return attendees;
	}

	private EventDateTime createDateTime(Day timePoint) throws ParseException {
		EventDateTime start = new EventDateTime();
		Date startDate = new SimpleDateFormat("d MMMM - yyyy").parse(timePoint.toString());
		start.setDateTime(new DateTime(startDate));
		return start;
	}
}

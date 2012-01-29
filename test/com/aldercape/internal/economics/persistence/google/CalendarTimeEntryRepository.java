package com.aldercape.internal.economics.persistence.google;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.aldercape.internal.economics.google.calendar.CalendarEntry;
import com.aldercape.internal.economics.google.calendar.EventBuilder;
import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.Day;
import com.aldercape.internal.economics.model.Entry;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

public class CalendarTimeEntryRepository implements TimeEntryReository {

	private static final String CLIENT_ID = "1001561526980.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "T31DmaikU2ZJK1oFR2lcRZSJ";
	private static ClientRepository clientRepository;
	private static CollaboratorRepository collaboratorRepository;
	private HttpTransport httpTransport;
	private JacksonFactory jsonFactory;
	private String calendarId;
	private String applicationName;
	private String accessToken;
	private String refreshTooken;

	public CalendarTimeEntryRepository(HttpTransport httpTransport, GoogleCalendarSettings calendarSettings) {
		this.httpTransport = httpTransport;
		disableCertificateValidation();
		jsonFactory = new JacksonFactory();
		this.calendarId = calendarSettings.calendarId();
		this.applicationName = calendarSettings.applicationName();
		this.accessToken = calendarSettings.accessTooken();
		this.refreshTooken = calendarSettings.refreshTooken();
	}

	@Override
	public List<Entry<Day>> findAll(Collaborator me) {
		Calendar service = createService();
		try {
			Events events = service.events().list(calendarId).execute();
			List<Entry<Day>> result = new ArrayList<Entry<Day>>();
			while (true) {
				for (Event event : events.getItems()) {
					if (event.getSummary() != null) {
						result.add(new CalendarEntry(event, collaboratorRepository, clientRepository));
					}
				}
				String pageToken = events.getNextPageToken();
				if (pageToken != null && !pageToken.isEmpty()) {
					events = service.events().list(calendarId).setPageToken(pageToken).execute();
				} else {
					break;
				}
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	@Override
	public void add(Entry<Day> entry) {
		try {
			Event event = new EventBuilder().convert(entry);
			createService().events().insert(calendarId, event).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Calendar createService() {
		GoogleAccessProtectedResource accessProtectedResource = new GoogleAccessProtectedResource(accessToken, httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, refreshTooken);
		Calendar service = Calendar.builder(httpTransport, jsonFactory).setApplicationName(applicationName).setHttpRequestInitializer(accessProtectedResource).build();
		return service;
	}

	public static void disableCertificateValidation() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
				return;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
				return;
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			return;
		}
	}

}

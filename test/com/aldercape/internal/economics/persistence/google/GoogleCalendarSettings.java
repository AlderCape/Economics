package com.aldercape.internal.economics.persistence.google;

public class GoogleCalendarSettings {
	private String calendarId;
	private String applicationName;
	private String accessTooken;
	private String refreshTooken;

	public GoogleCalendarSettings(String calendarId, String applicationName, String accessTooken, String refreshTooken) {
		this.calendarId(calendarId);
		this.applicationName(applicationName);
		this.accessTooken(accessTooken);
		this.refreshTooken(refreshTooken);
	}

	public String calendarId() {
		return calendarId;
	}

	public void calendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	public String applicationName() {
		return applicationName;
	}

	public void applicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String accessTooken() {
		return accessTooken;
	}

	public void accessTooken(String accessTooken) {
		this.accessTooken = accessTooken;
	}

	public String refreshTooken() {
		return refreshTooken;
	}

	public void refreshTooken(String refreshTooken) {
		this.refreshTooken = refreshTooken;
	}
}
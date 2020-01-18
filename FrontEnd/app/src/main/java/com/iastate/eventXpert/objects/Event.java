package com.iastate.eventXpert.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Event that contains the information returned from the API and needed to display on the UI
 */
public class Event implements Serializable {

	/**
	 * The id used as the unique identifier for this event
	 */
	private Integer id;

	/**
	 * The name of this event
	 */
	private String name;

	/**
	 * The location of this event
	 */
	private String location;

	/**
	 * The description of this event
	 */
	private String description;

	/**
	 * The Date and Time that this event starts
	 */
	private Date startDateTime;

	/**
	 * The Date and Time that this event ends
	 */
	private Date endDateTime;

	/**
	 * A calendar used by this event to simplify DateTime calculations
	 */
	private Calendar cal;

	/**
	 * Default Constructor for Event
	 */
	public Event() {
	}

	/**
	 * Constructor for event from JSON String
	 *
	 * @param jsonString A JSON string containing event information (id, name, etc.)
	 *                   In the format {"id":1,"name":"thing","location":"thing","description"...}
	 */
	public Event(String jsonString) {

		JSONObject eventInfo;

		try {
			eventInfo = new JSONObject(jsonString);
			id = eventInfo.getInt("id");
			name = eventInfo.getString("name");
			location = eventInfo.getString("location");
			description = eventInfo.getString("description");
			startDateTime = dateFromString(eventInfo.getString("startDateTime"));
			endDateTime = dateFromString(eventInfo.getString("endDateTime"));
			cal = Calendar.getInstance(TimeZone.getTimeZone("CST"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * returns the event id
	 *
	 * @return the id for this event
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * returns the name
	 *
	 * @return the name for this event
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns the location
	 *
	 * @return the location for this event
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * returns the description
	 *
	 * @return the description for this event
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * returns the startDateTime
	 *
	 * @return the startDateTime for this event
	 */
	public Date getStartDateTime() {
		return startDateTime;
	}

	/**
	 * returns the endDateTime
	 *
	 * @return the endDateTime for this event
	 */
	public Date getEndDateTime() {
		return endDateTime;
	}

	/**
	 * Convert java.util.Date to date string
	 *
	 * @param dateTime to get date from
	 *
	 * @return date as a string in form yyyy-mm-dd
	 */
	public String getDateString(Date dateTime) {
		cal.setTime(dateTime);
		return String.format(
				"%04d-%02d-%02d",
				cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * Convert Date to time string
	 *
	 * @param dateTime to get time from
	 *
	 * @return time as a string in the form hh:mm
	 */
	public String getTimeString(Date dateTime) {
		cal.setTime(dateTime);
		return String.format(
				"%02d:%02d",
				cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE));
	}

	/**
	 * This function takes a JSON date in string form and turns it into a java.util.Date
	 *
	 * @param jsonDate a DateTime in string format as it would be found in a JSONObject
	 *
	 * @return java.util.Date from given String
	 */
	private Date dateFromString(String jsonDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		formatter.setTimeZone(TimeZone.getTimeZone("CST"));
		try {
			return formatter.parse(jsonDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}

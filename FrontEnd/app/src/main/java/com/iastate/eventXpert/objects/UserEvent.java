package com.iastate.eventXpert.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * UserEvent that contains the necessary user and event information.
 */
public class UserEvent {

	/**
	 * The id for this UserEvent
	 */
	private Integer userEventId;

	/**
	 * The id for the associated User
	 */
	private Integer userId;

	/**
	 * The id for the associated Event
	 */
	private Integer eventId;

	/**
	 * Denotes if the user is checked in to this event
	 */
	private boolean isCheckedIn;

	/**
	 * Denotes if the user is an admin for this event
	 */
	private boolean isAdmin;

	/**
	 * Default constructor
	 */
	public UserEvent() {
	}

	/**
	 * Constructor for event from JSON String
	 *
	 * @param jsonString A JSON string containing event information (id, userId, etc.)
	 *                   In the format {"userEventId":1,"userId":2,"isAdmin":false,"eventId"...}
	 */
	public UserEvent(String jsonString) {
		JSONObject userEventInfo;

		try {
			userEventInfo = new JSONObject(jsonString);
			userEventId = userEventInfo.getInt("userEventId");
			userId = userEventInfo.getInt("userId");
			eventId = userEventInfo.getInt("eventId");
			isAdmin = userEventInfo.getBoolean("isAdmin");
			isCheckedIn = userEventInfo.getBoolean("isCheckedIn");

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * returns the UserEventId
	 *
	 * @return the UserEventId for this UserEvent
	 */
	public Integer getUserEventId() {
		return userEventId;
	}

	/**
	 * returns the UserId
	 *
	 * @return the UserId for the User associated with this UserEvent
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * returns the EventId
	 *
	 * @return the EventId for the Event associated with this UserEvent
	 */
	public Integer getEventId() {
		return eventId;
	}

	/**
	 * returns whether the user is the admin for this event
	 *
	 * @return true if the associated User is an admin for the associated event otherwise false
	 */
	public boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * returns whether the user is checked in to this event
	 *
	 * @return true if the associated User is checked in to the associated event otherwise false
	 */
	public boolean getIsCheckedIn() {
		return isCheckedIn;
	}

}

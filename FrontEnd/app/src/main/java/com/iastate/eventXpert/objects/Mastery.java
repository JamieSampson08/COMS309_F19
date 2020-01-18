package com.iastate.eventXpert.objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents a Mastery
 */
public class Mastery {
	private Integer id;

	private String name;

	private int pointsNeeded;

	private int maxPoints;

	private int registerPoints;

	private int checkInPoints;

	private int createEventPoints;

	/**
	 * Default constructor
	 */
	public Mastery(String jsonString) {
		JSONObject masteryInfo;

		try {
			masteryInfo = new JSONArray(jsonString).getJSONObject(0);
			id = masteryInfo.getInt("id");
			name = masteryInfo.getString("name");
			pointsNeeded = masteryInfo.getInt("pointsNeeded");
			maxPoints = masteryInfo.getInt("maxPoints");
			registerPoints = masteryInfo.getInt("registerPoints");
			checkInPoints = masteryInfo.getInt("checkInPoints");
			createEventPoints = masteryInfo.getInt("createEventPoints");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPointsNeeded() {
		return pointsNeeded;
	}

	public int getMaxPoints() {
		return maxPoints;
	}

	public int getRegisterPoints() {
		return registerPoints;
	}

	public int getCheckInPoints() {
		return checkInPoints;
	}

	public int getCreateEventPoints() {
		return createEventPoints;
	}
}

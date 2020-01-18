package com.iastate.eventXpert.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * User that contains the necessary fields returned from the API and displayed on the UI.
 */
public class User implements Serializable {

	/**
	 * The id used to identify this User
	 */
	private Integer id;

	/**
	 * The first name of this User
	 */
	private String firstName;

	/**
	 * The last name of this User
	 */
	private String lastName;

	/**
	 * The email of this User
	 */
	private String email;

	/**
	 * How many points the user has
	 */
	private int points;

	private String profileFileName;

	transient private Mastery mastery;

	/**
	 * Default constructor
	 */
	public User() {
	}

	/**
	 * User constructed from a String returned from the API
	 *
	 * @param jsonstring string returned from the API
	 */
	public User(String jsonstring) {
		JSONObject userInfo;

		try {
			userInfo = new JSONObject(jsonstring);
			id = userInfo.getInt("id");
			firstName = userInfo.getString("firstName");
			lastName = userInfo.getString("lastName");
			email = userInfo.getString("email");
			profileFileName = userInfo.getString("profileFileName");
			points = userInfo.getInt("points");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return's user's id
	 *
	 * @return user's id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Returns user's first name
	 *
	 * @return first name of user
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns user's last name
	 *
	 * @return last name of user
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Return's user's email
	 *
	 * @return email of user
	 */
	public String getEmail() {
		return email;
	}

	public String getProfileFileName() {
		return profileFileName;
	}

	public int getPoints() {
		return points;
	}

	public void setMastery(Mastery mastery) {
		this.mastery = mastery;
	}

	public Mastery getMastery() {
		return mastery;
	}
}

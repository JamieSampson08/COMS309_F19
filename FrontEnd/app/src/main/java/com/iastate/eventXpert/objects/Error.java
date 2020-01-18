package com.iastate.eventXpert.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Error class containing information about errors that occur
 */
public class Error {
	private Integer status;
	private String error;
	private String message;
	private String path;

	/**
	 * Default Constructor
	 */
	public Error() {
	}

	/**
	 * Creates an Error object
	 *
	 * @param jsonString of data to construct error
	 */
	public Error(String jsonString) {
		JSONObject errorInfo;

		try {
			errorInfo = new JSONObject(jsonString);
			status = errorInfo.getInt("status");
			error = errorInfo.getString("error");
			message = errorInfo.getString("message");
			path = errorInfo.getString("path");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns status of error
	 *
	 * @return int status value
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Returns api path that was hit
	 *
	 * @return string API path hit
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Returns error message
	 *
	 * @return string error message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns what kind of error occurred
	 *
	 * @return string type of error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Builds an error message containing both status and message
	 *
	 * @return string error message
	 */
	public String getFullMessage() {
		return String.format("%d: %s", status, message);
	}
}

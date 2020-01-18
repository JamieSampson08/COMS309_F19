package com.iastate.eventXpert.constants;

/**
 * UrlConstants used to control which vesion of the API is called
 */
public final class UrlConstants {

	/**
	 * The IP Address of the local user
	 */
	public static final String IP_ADDRESS = "192.168.56.1";

	/**
	 * The url to be used to hit the API running locally
	 */
	public static final String LOCALHOST_URL = "http://" + IP_ADDRESS + ":8080";

	/**
	 * The url for the API hosted on the server, comment out this line, and uncomment the one below to run the API locally
	 */
	public static final String BASE_URL = "http://coms-309-MG-3.misc.iastate.edu:8080";

	/**
	 * The line below should always be commented in master, but should be used when a user wants to run the API locally
	 */
	//public static final String BASE_URL = LOCALHOST_URL;

	public static final String BASE_PROFILE_IMAGE_URL = "http://coms-309-mg-3.misc.iastate.edu/profile-pictures/";

	public static final String BASE_MASTERY_IMAGE_URL = "http://coms-309-mg-3.misc.iastate.edu/mastery-images/";
}

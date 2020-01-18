package com.iastate.eventXpert.constants;

import org.java_websocket.client.WebSocketClient;

import static com.iastate.eventXpert.constants.UrlConstants.IP_ADDRESS;

public class SocketConstants {
	/**
	 * Global socket variable
	 */
	public static WebSocketClient socket;

	public static final String LOACLHOST_WEB_SOCKET_URL = "ws://" + IP_ADDRESS + ":8080/websocket";

	/**
	 * The url for the API hosted on the server, comment out this line, and uncomment the one below to run the API locally
	 */
	public static final String WEB_SOCKET_URL = "ws://coms-309-MG-3.misc.iastate.edu:8080/websocket";

	/**
	 * The line below should always be commented in master, but should be used when a user wants to run the API locally
	 */
	//public static final String WEB_SOCKET_URL = LOACLHOST_WEB_SOCKET_URL;
}

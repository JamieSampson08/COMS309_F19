package com.iastate.eventXpert.helpers;

import android.util.Log;

import static com.iastate.eventXpert.constants.SocketConstants.*;

public class SocketHelper {

	/**
	 * To send a message to the server to send to clients
	 */
	public static void sendMessage(String eventName) {
		try {
			socket.send(eventName);
		} catch (Exception e) {
			Log.d("ExceptionSendMessage", e.getMessage().toString());
			e.printStackTrace();
		}
	}
}

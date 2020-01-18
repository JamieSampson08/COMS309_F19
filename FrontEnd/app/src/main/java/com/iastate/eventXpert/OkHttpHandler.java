package com.iastate.eventXpert;

import android.os.AsyncTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttpHandler that executes API requests as an AsyncTask
 */
public class OkHttpHandler extends AsyncTask {

	/**
	 * The client that executes the API requests
	 */
	OkHttpClient client = new OkHttpClient();

	/**
	 * actions that should happen after the API request is executed
	 *
	 * @param o the object that is executed
	 */
	@Override
	protected void onPostExecute(Object o) {
		super.onPostExecute(o);
	}

	/**
	 * Actions that should happen during the background of the program
	 *
	 * @param objects the API requests to be executed
	 *
	 * @return the result of the API call
	 */
	@Override
	protected Object doInBackground(Object[] objects) {
		Request request;

		if (objects[0].getClass() == String.class) {
			Request.Builder builder = new Request.Builder();
			builder.url(objects[0].toString());
			request = builder.build();
		} else {
			request = (Request) objects[0];
		}

		try {
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}


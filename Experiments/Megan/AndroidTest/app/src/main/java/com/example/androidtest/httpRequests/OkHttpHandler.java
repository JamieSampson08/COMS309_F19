package com.example.androidtest.httpRequests;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpHandler extends AsyncTask {

	OkHttpClient client = new OkHttpClient();

	@Override
	protected void onPostExecute(Object o) {
		System.out.println("o: " + o);
		super.onPostExecute(o);
	}

	@Override
	protected Object doInBackground(Object[] objects) {
		System.out.println("objects: " + objects);
		System.out.println("objects[0].toString(): " + objects[0].toString());

		Request request;

		if(objects[0].getClass() == String.class) {
			Request.Builder builder = new Request.Builder();
			builder.url(objects[0].toString());
			request = builder.build();
		} else {
			request = (Request)objects[0];
		}

		try {
			Response response = client.newCall(request).execute();
			return response.body().string();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
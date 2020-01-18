package com.iastate.eventXpert.helpers;

import android.widget.Toast;

import com.iastate.eventXpert.OkHttpHandler;
import com.iastate.eventXpert.objects.Error;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.mikepenz.iconics.Iconics.getApplicationContext;

public class APIHelper {
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public static Object makePOSTRequest(String url, JSONObject json) {
		try {
			OkHttpHandler okHttpHandler = new OkHttpHandler();
			RequestBody body = RequestBody.create(JSON, json.toString());
			Request request = new Request.Builder()
					.url(url)
					.post(body)
					.build();

			Object response = okHttpHandler.execute(request).get();

			if (response == null || response.toString().indexOf("error") != -1) {
				Error error = new Error(response.toString());
				Toast.makeText(getApplicationContext(), error.getFullMessage(), Toast.LENGTH_LONG).show();
			} else {
				return response;
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
			String errorMsg = "Error: InterruptedException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		} catch (ExecutionException e) {
			e.printStackTrace();
			String errorMsg = "Error: ExecutionException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		}
		return null;
	}

	public static Object makeGETRequest(String url) {
		try {
			OkHttpHandler okHttpHandler = new OkHttpHandler();
			Request request = new Request.Builder()
					.url(url)
					.get()
					.build();

			Object response = okHttpHandler.execute(request).get();

			if (response == null || response.toString().indexOf("error") != -1) {
				Error error = new Error(response.toString());
				Toast.makeText(getApplicationContext(), error.getFullMessage(), Toast.LENGTH_LONG).show();
			} else {
				return response;
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
			String errorMsg = "Error: InterruptedException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		} catch (ExecutionException e) {
			e.printStackTrace();
			String errorMsg = "Error: ExecutionException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		}
		return null;
	}

	public static Object makePUTRequest(String url, JSONObject json) {
		try {
			OkHttpHandler okHttpHandler = new OkHttpHandler();
			RequestBody body = RequestBody.create(JSON, json.toString());
			Request request = new Request.Builder()
					.url(url)
					.put(body)
					.build();

			Object response = okHttpHandler.execute(request).get();

			if (response == null || response.toString().indexOf("error") != -1) {
				Error error = new Error(response.toString());
				Toast.makeText(getApplicationContext(), error.getFullMessage(), Toast.LENGTH_LONG).show();
			} else {
				return response;
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
			String errorMsg = "Error: InterruptedException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		} catch (ExecutionException e) {
			e.printStackTrace();
			String errorMsg = "Error: ExecutionException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		}
		return null;
	}
}

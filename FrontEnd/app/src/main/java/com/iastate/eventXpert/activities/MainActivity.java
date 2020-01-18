package com.iastate.eventXpert.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.iastate.eventXpert.adapters.EventArrayAdapter;
import com.iastate.eventXpert.R;
import com.iastate.eventXpert.helpers.APIHelper;
import com.iastate.eventXpert.helpers.NavigationHelper;
import com.iastate.eventXpert.objects.Error;
import com.iastate.eventXpert.objects.Event;
import com.iastate.eventXpert.objects.User;

import static com.iastate.eventXpert.constants.UrlConstants.*;
import static com.iastate.eventXpert.constants.SocketConstants.*;

/**
 * This is the MainActivity of the eventXpert Android application
 */
public class MainActivity extends AppCompatActivity implements EventArrayAdapter.OnEventListener {
	/**
	 * The recycler view that holds the events
	 */
	RecyclerView eventRecyclerView;

	/**
	 * Formats the eventRecyclerView
	 */
	private EventArrayAdapter eventArrayAdapter;

	/**
	 * Holds the list of allEvents for display on the homescreen
	 */
	private ArrayList<Event> allEvents;

	/**
	 * Used to catch problems with userEvents
	 */
	Error error;

	/**
	 * Use to send userId to socket on server side
	 */
	User user;

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.create_event_fab)
	FloatingActionButton createEventFab;

	@BindView(R.id.show_all_events_button)
	Button showAllEventsButton;

	@BindView(R.id.all_events_text)
	TextView allEventsText;

	@BindView(R.id.user_events_text)
	TextView userEventsText;

	@BindView(R.id.user_events_recycle_view)
	RecyclerView userEventsRecycleView;

	@BindView(R.id.all_events_recycle_view)
	RecyclerView allEventsRecycleView;

	/**
	 * Calls the general onStart methods and fills the top screen with user specific events.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		userEvents();
		if (showAllEventsButton.getVisibility() == View.GONE) {
			showAllEvents();
		}
	}

	/**
	 * Controls display of events, scrolling, selecting menu items, etc.
	 * Includes buttons such as "Show all events" and access to settings page.
	 *
	 * @param savedInstanceState previous savedInstance
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		// clears data from file
		deleteFile("Notifications.txt");

		fetchUser();
		connectWebSocket();
		userEvents();
		allEventsText.setVisibility(View.GONE);
		NavigationHelper.createDrawer(this, toolbar);
	}

	/**
	 * Sets up the Android application with the EventActivity, Menu bar, and eventRecyclerView
	 */
	@OnClick(R.id.create_event_fab)
	public void goToCreateEvent() {
		Intent createEventActivity = new Intent(this.getBaseContext(), EventDetailsActivity.class);
		this.startActivityForResult(createEventActivity, 0);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		eventRecyclerView.setLayoutManager(llm);
	}

	/**
	 * Handles a client connecting to the WebSocket
	 */
	public void connectWebSocket() {
		Draft[] drafts = {new Draft_6455()};
		String url = WEB_SOCKET_URL + "/" + user.getId();

		try {
			Log.d("Socket", "Trying Socket");
			socket = new WebSocketClient(new URI(url), (Draft) drafts[0]) {
				@Override
				public void onMessage(String message) {
					Log.d("", "run() returned: " + message);
					showSocketToast(message, getApplicationContext());
				}

				@Override
				public void onOpen(ServerHandshake handshake) {
					Log.d("OPEN", "run() returned: is connecting");
				}

				@Override
				public void onClose(int code, String reason, boolean remote) {
					Log.d("CLOSE", "onClose() returned " + reason);
				}

				@Override
				public void onError(Exception e) {
					Log.d("EXCEPTION", e.toString());
				}
			};
		} catch (URISyntaxException e) {
			Log.d("EXCEPTION", e.getMessage().toString());
			e.printStackTrace();
		}
		socket.connect();
	}

	/**
	 * Since the onMessage function of the WebSocket is outside the UI thread,
	 * this function runs the toast on the UI thread using the supplied message
	 * from the socket
	 *
	 * @param message to be in toast
	 */
	public void showSocketToast(String message, Context context) {
		final String str = message;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context, str, Toast.LENGTH_LONG).show();
				writeFile();
			}

			public void writeFile() {
				try {
					String formatStr = str + "\n";
					FileOutputStream fileOutputStream = openFileOutput("Notifications.txt", MODE_APPEND);
					fileOutputStream.write(formatStr.getBytes());
					fileOutputStream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Calls the get endpoint of users to associate a
	 * user with the current session
	 * REMOVE LATER - once Session Users enabled
	 */
	private void fetchUser() {
		String url = String.format("%s/users/%s", BASE_URL, SignInActivity.sessionUserId);
		Object res = APIHelper.makeGETRequest(url);
		if (res != null) {
			// set global User variable
			user = new User(res.toString());
		}
	}

	/**
	 * Handles the API call to get events associated with a given user.
	 * This will eventually be modified to get the session user instead of hardcoded number in "userEventsUrl"
	 */
	private void userEvents() {
		String url = BASE_URL + "/userEvents/users/" + SignInActivity.sessionUserId + "/events";
		try {
			Object res = APIHelper.makeGETRequest(url);
			if (res != null) {
				if (!(res.toString().equals("[]"))) {
					userEventsText.setText("My Events:");
				}
			}
			buildEventsList(res, userEventsRecycleView);
		} catch (JSONException e) {
			e.printStackTrace();
			String errorMsg = "Error: JSONException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * displayAllEvents handles the call to get all events and display them on the
	 * second Recyclerview
	 */
	@OnClick(R.id.show_all_events_button)
	public void showAllEvents() {
		String url = BASE_URL + "/events";
		try {
			Object res = APIHelper.makeGETRequest(url);
			if (res != null) {
				showAllEventsButton.setVisibility(View.GONE);
				allEventsText.setText("All Events:");
				allEventsText.setVisibility(View.VISIBLE);
				buildEventsList(res, allEventsRecycleView);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			String errorMsg = "Error: JSONException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * buildEvents takes JSON String/object of events and places them into a given Recyclerview
	 * layout on the home page
	 */
	private void buildEventsList(Object givenEvents, RecyclerView list) throws JSONException {
		JSONArray arrEvents = new JSONArray(givenEvents.toString());
		allEvents = new ArrayList<Event>();
		int i = 0;

		//Build Events List
		while (!(arrEvents.isNull(i))) {
			String temp = arrEvents.getString(i);
			allEvents.add(new Event(temp));
			i++;
		}

		eventArrayAdapter = new EventArrayAdapter(R.layout.cards_layout, allEvents, this);
		eventRecyclerView = list;
		eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		eventRecyclerView.setItemAnimator(new DefaultItemAnimator());
		eventRecyclerView.setAdapter(eventArrayAdapter);
	}

	/**
	 * Handles clicking of event on screen. On an event click this takes user to the
	 * respective event page selected.
	 *
	 * @param position where the click happened on screen
	 */
	@Override
	public void onEventClick(int position) {
		Event event = allEvents.get(position);
		Intent i = new Intent(this, EventDetailsActivity.class);
		i.putExtra("event", event);
		startActivity(i);
	}
}

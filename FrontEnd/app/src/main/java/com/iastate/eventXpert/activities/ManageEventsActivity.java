package com.iastate.eventXpert.activities;

import android.content.Intent;
import android.os.Bundle;

import com.iastate.eventXpert.adapters.EventArrayAdapter;
import com.iastate.eventXpert.R;
import com.iastate.eventXpert.helpers.APIHelper;
import com.iastate.eventXpert.helpers.NavigationHelper;
import com.iastate.eventXpert.objects.Event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.iastate.eventXpert.constants.UrlConstants.BASE_URL;

public class ManageEventsActivity extends AppCompatActivity implements EventArrayAdapter.OnEventListener {

	@BindView(R.id.manageEventsText)
	TextView textView;

	@BindView(R.id.manageEvents)
	RecyclerView eventRecyclerView;

	/**
	 * Formats the eventRecyclerView
	 */
	private EventArrayAdapter eventArrayAdapter;

	private ArrayList<Event> allEvents;

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
					textView.setText("My Events:");
				}
			}
			buildEventsList(res);
		} catch (JSONException e) {
			e.printStackTrace();
			String errorMsg = "Error: JSONException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		}
	}

	private void buildEventsList(Object givenEvents) throws JSONException {
		JSONArray arrEvents = new JSONArray(givenEvents.toString());
		allEvents = new ArrayList<>();
		int i = 0;

		//Build Events List
		while (!(arrEvents.isNull(i))) {
			String temp = arrEvents.getString(i);
			allEvents.add(new Event(temp));
			i++;
		}

		eventArrayAdapter = new EventArrayAdapter(R.layout.cards_layout, allEvents, this);
		eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		eventRecyclerView.setItemAnimator(new DefaultItemAnimator());
		eventRecyclerView.setAdapter(eventArrayAdapter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_events);
		ButterKnife.bind(this);
		Toolbar toolbar = findViewById(R.id.toolbar);
		userEvents();
		setSupportActionBar(toolbar);
		NavigationHelper.createDrawer(this, toolbar);
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
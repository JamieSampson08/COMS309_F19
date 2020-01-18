package com.iastate.eventXpert.activities;

import android.content.Intent;
import android.os.Bundle;

import com.iastate.eventXpert.adapters.EventArrayAdapter;
import com.iastate.eventXpert.R;
import com.iastate.eventXpert.fragments.SortPickerFragment;
import com.iastate.eventXpert.helpers.APIHelper;
import com.iastate.eventXpert.helpers.NavigationHelper;
import com.iastate.eventXpert.objects.Error;
import com.iastate.eventXpert.objects.Event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.iastate.eventXpert.constants.UrlConstants.BASE_URL;

public class FindEventsActivity extends AppCompatActivity implements EventArrayAdapter.OnEventListener {

	@BindView(R.id.search_view)
	SearchView searchView;

	@BindView(R.id.findEventsText)
	TextView textView;

	@BindView(R.id.findEvents)
	RecyclerView eventRecyclerView;

	@BindView(R.id.showSortSelectorButton)
	Button showSortSelectorButton;

	/**
	 * Formats the eventRecyclerView
	 */
	private EventArrayAdapter eventArrayAdapter;

	private ArrayList<Event> allEvents;

	public void queryTextSubmit() {
		String query = searchView.getQuery().toString();
		if (query == null || query.length() < 1) {
			textView.setText(R.string.no_empty_search);
			return;
		}

		String url;
		boolean singleEvent;

		textView.setVisibility(View.GONE);

		if (query.matches("\\d+")) {
			int id = Integer.parseInt(query);
			url = BASE_URL + "/events/" + id;
			singleEvent = true;
		} else {
			singleEvent = false;
			url = BASE_URL + "/events?name=" + query;
			if (SortPickerFragment.getSelectedOption() != null && !SortPickerFragment.getSelectedOption().equals("none")) {
				url += "&sort=" + SortPickerFragment.getSelectedOption();
				textView.setVisibility(View.VISIBLE);
				textView.setText("Sorted By: " + SortPickerFragment.getSelectedOption());
			}
		}

		try {
			Object res = APIHelper.makeGETRequest(url);
			showSortSelectorButton.setVisibility(View.VISIBLE);

			if (res == null) {
				Error error = new Error(res.toString());
				textView.setText(R.string.no_empty_search);
				textView.setVisibility(View.VISIBLE);
				showSortSelectorButton.setVisibility(View.GONE);
				Toast.makeText(getApplicationContext(), error.getFullMessage(), Toast.LENGTH_LONG).show();
				buildEventsList("[]");
				return;
			} else if (res.toString().equals("[]")) {
				textView.setText(R.string.no_events_found);
				textView.setVisibility(View.VISIBLE);
				showSortSelectorButton.setVisibility(View.GONE);
				buildEventsList("[]");
				return;
			}

			buildEventsList(singleEvent ? "[" + res.toString() + "]" : res);
		} catch (JSONException e) {
			e.printStackTrace();
			String errorMsg = "Error: JSONException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		}
	}

	private void buildEventsList(Object givenEvents) throws JSONException {
		JSONArray arrEvents = new JSONArray(givenEvents.toString());
		if (arrEvents.length() <= 1) {
			showSortSelectorButton.setVisibility(View.GONE);
		}
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

	@OnClick(R.id.showSortSelectorButton)
	public void showSortSelectorDialog() {
		DialogFragment newFragment = new SortPickerFragment();
		newFragment.show(getSupportFragmentManager(), searchView.getQuery().toString());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_events);
		ButterKnife.bind(this);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				queryTextSubmit();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});
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

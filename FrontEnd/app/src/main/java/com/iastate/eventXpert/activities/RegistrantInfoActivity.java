package com.iastate.eventXpert.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.iastate.eventXpert.R;
import com.iastate.eventXpert.adapters.RegistrantArrayAdapter;
import com.iastate.eventXpert.helpers.APIHelper;
import com.iastate.eventXpert.objects.Event;
import com.iastate.eventXpert.objects.Registrant;
import com.iastate.eventXpert.objects.User;
import com.iastate.eventXpert.objects.UserEvent;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.iastate.eventXpert.constants.UrlConstants.BASE_URL;

/**
 * RegistrantInfoActivity is the activity which controllers the view when used for viewing registrants of an event
 */
public class RegistrantInfoActivity extends AppCompatActivity implements RegistrantArrayAdapter.OnEventListener {
	/**
	 * The recycler view used to show multiple registrant cards
	 */
	@BindView(R.id.registrants)
	RecyclerView registrantRecyclerView;

	/**
	 * The header for the page
	 */
	@BindView(R.id.registrant_header)
	TextView registrantHeader;

	/**
	 * The arrayAdapter used for registrantRecyclerView
	 */
	private RegistrantArrayAdapter registrantArrayAdapter;

	/**
	 * An ArrayList of registrants
	 */
	private ArrayList<Registrant> allRegistrants;

	/**
	 * Sets the contentView when the activity is created
	 *
	 * @param savedInstanceState Bundle of the saved instance state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrant_info);
		ButterKnife.bind(this);
	}

	/**
	 * Fetches users for this particular event onStart
	 */
	@Override
	protected void onStart() {
		super.onStart();
		getUsersForEvent();
	}

	/**
	 * Make a call to the API to get the users associated with this event
	 */
	private void getUsersForEvent() {
		Event thisEvent = (Event) getIntent().getSerializableExtra("event");
		try {
			String url = BASE_URL + "/userEvents/events/" + thisEvent.getId() + "/users";
			Object res = APIHelper.makeGETRequest(url);
			if (res != null) {
				if (res.toString().equals("[]")) {
					registrantHeader.setText("It looks like there are not users for this event.\n How did you get here?");
				} else {
					registrantHeader.setText("All participants:");
				}
			}
			buildUsersList(res);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Find a specific user given the userId and the eventId
	 *
	 * @param userId  the userId of the user being searched for
	 * @param eventId the eventId of the event being searched for
	 *
	 * @return The UserEvent matching the userId and the eventId
	 *
	 * @throws ExecutionException   if execution of API call fails
	 * @throws InterruptedException if HTTPRequest is interrupted
	 */
	private UserEvent getUserEventByUserIdAndEventId(Integer userId, Integer eventId) throws ExecutionException, InterruptedException {
		String url = BASE_URL + "/userEvents/users/" + userId + "/events/" + eventId;
		Object res = APIHelper.makeGETRequest(url);
		if (res != null) {
			return new UserEvent(res.toString());
		}
		return null;
	}

	/**
	 * Constructs a list of all users for a specific event
	 *
	 * @param givenUsers an Object (JSON) of the users returned from the API
	 *
	 * @throws JSONException        if JSON can not be parsed
	 * @throws ExecutionException   if execution can not be executed
	 * @throws InterruptedException if procedures are interrupted
	 */
	private void buildUsersList(Object givenUsers) throws JSONException, ExecutionException, InterruptedException {
		Event thisEvent = (Event) getIntent().getSerializableExtra("event");
		JSONArray arrUsers = new JSONArray(givenUsers.toString());
		allRegistrants = new ArrayList<Registrant>();

		for (int i = 0; !(arrUsers.isNull(i)); i++) {
			String temp = arrUsers.getString(i);
			User tempUser = new User(temp);
			UserEvent tempUserEvent = getUserEventByUserIdAndEventId(tempUser.getId(), thisEvent.getId());
			Registrant tempRegistrant = new Registrant(tempUser, tempUserEvent);
			allRegistrants.add(tempRegistrant);
		}

		registrantArrayAdapter = new RegistrantArrayAdapter(R.layout.registrant_cards_layout, allRegistrants, this);

		registrantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		registrantRecyclerView.setItemAnimator(new DefaultItemAnimator());
		registrantRecyclerView.setAdapter(registrantArrayAdapter);
	}

	/**
	 * Handles clicking a user the on screen. On an user click this takes user to the
	 * respective user page selected.
	 *
	 * @param position where the click happened on screen
	 */
	@Override
	public void onEventClick(int position) {
		Registrant registrant = allRegistrants.get(position);
		User user = registrant.getAssociatedUser();
		Intent i = new Intent(getApplicationContext(), UserDetailsActivity.class);
		i.putExtra("user", user);
		startActivity(i);
	}
}

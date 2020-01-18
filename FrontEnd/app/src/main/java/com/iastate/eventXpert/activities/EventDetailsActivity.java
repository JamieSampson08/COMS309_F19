package com.iastate.eventXpert.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.iastate.eventXpert.OkHttpHandler;
import com.iastate.eventXpert.R;
import com.iastate.eventXpert.SuccessDialog;
import com.iastate.eventXpert.enums.DateTimePicker;
import com.iastate.eventXpert.fragments.DatePickerFragment;
import com.iastate.eventXpert.fragments.TimePickerFragment;
import com.iastate.eventXpert.helpers.APIHelper;
import com.iastate.eventXpert.helpers.SocketHelper;
import com.iastate.eventXpert.objects.Event;
import com.iastate.eventXpert.objects.User;
import com.iastate.eventXpert.objects.UserEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import okhttp3.MediaType;

import static com.iastate.eventXpert.constants.UrlConstants.*;

/**
 * Activity to handle all interactions on the Event Details page
 */
public class EventDetailsActivity extends AppCompatActivity {
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	@BindView(R.id.event_name_edit_text)
	EditText nameEditText;

	@BindView(R.id.event_name_view)
	View eventNameView;

	@BindView(R.id.location_edit_text)
	EditText locationEditText;

	@BindView(R.id.location_view)
	View locationView;

	@BindView(R.id.description_edit_text)
	EditText descriptionEditText;

	@BindView(R.id.description_view)
	View descriptionView;

	@BindView(R.id.start_date_edit_text)
	EditText startDateEditText;

	@BindView(R.id.start_date_view)
	View startDateView;

	@BindView(R.id.start_time_edit_text)
	EditText startTimeEditText;

	@BindView(R.id.start_time_view)
	View startTimeView;

	@BindView(R.id.end_date_edit_text)
	EditText endDateEditText;

	@BindView(R.id.end_date_view)
	View endDateView;

	@BindView(R.id.end_time_edit_text)
	EditText endTimeEditText;

	@BindView(R.id.end_time_view)
	View endTimeView;

	@BindView(R.id.create_event_button)
	Button createEventButton;

	@BindView(R.id.save_event_button)
	Button saveEventButton;

	@BindView(R.id.edit_event_button)
	Button editEventButton;

	@BindView(R.id.check_in_event_button)
	Button checkInEventButton;

	@BindView(R.id.register_event_button)
	Button registerEventButton;

	@BindView(R.id.checked_in_text)
	TextView checkedInText;

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.view_participant_button)
	Button viewParticipantButton;

	@BindViews({
			R.id.end_time_edit_text,
			R.id.end_date_edit_text,
			R.id.start_time_edit_text,
			R.id.start_date_edit_text,
			R.id.event_name_edit_text,
			R.id.location_edit_text,
			R.id.description_edit_text
	})
	List<EditText> allEventEditTextViews;

	@BindViews({
			R.id.end_time_view,
			R.id.end_date_view,
			R.id.start_time_view,
			R.id.start_date_view,
			R.id.event_name_view,
			R.id.location_view,
			R.id.description_view
	})
	List<View> allEventViews;

	// globals for event
	String eventName;
	String location;
	String description;
	String startDate;
	String endDate;
	String startTime;
	String endTime;

	Event event;
	UserEvent userEvent;
	User user;

	/**
	 * Sets up the event details page either with the data of an event
	 * or empty text fields to create an event
	 * Also handles what buttons appear depending on isAdmin, isCheckedIn
	 *
	 * @param savedInstanceState state of activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		ButterKnife.bind(this);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		event = null;
		userEvent = null;
		user = null;

		fetchUser();
		// if an event was passed through intent, set all values to given event values
		event = (Event) getIntent().getSerializableExtra("event");

		if (event != null) {
			setEventFields();
		}
		getCurrentData();

		if (event != null && user != null) {
			fetchUserEvent();
		}

		if (event == null) {
			// create an event
			createEventButton.setVisibility(View.VISIBLE);
			saveEventButton.setVisibility(View.GONE);
			editEventButton.setVisibility(View.GONE);
			registerEventButton.setVisibility(View.GONE);
			viewParticipantButton.setVisibility(View.GONE);
			checkInEventButton.setVisibility(View.GONE);
			checkedInText.setVisibility(View.GONE);
		} else {
			// event exists
			if (userEvent != null && userEvent.getIsAdmin()) {
				// edit event mode
				editEventButton.setVisibility(View.VISIBLE);
				viewParticipantButton.setVisibility(View.VISIBLE);

				saveEventButton.setVisibility(View.GONE);
				createEventButton.setVisibility(View.GONE);
				checkInEventButton.setVisibility(View.GONE);
				registerEventButton.setVisibility(View.GONE);
				checkedInText.setVisibility(View.GONE);
			} else {
				// userEvent exists
				if (userEvent != null) {
					if (userEvent.getIsCheckedIn()) {
						checkedInText.setVisibility(View.VISIBLE);
						checkInEventButton.setVisibility(View.GONE);
					} else {
						checkInEventButton.setVisibility(View.VISIBLE);
						// if (currentDate == startDate){
						checkInEventButton.setEnabled(true);
						// }
						checkedInText.setVisibility(View.GONE);
					}
					registerEventButton.setVisibility(View.GONE);
				} else {
					// register for event
					registerEventButton.setVisibility(View.VISIBLE);
					checkInEventButton.setVisibility(View.GONE);
					checkedInText.setVisibility(View.GONE);
				}
				createEventButton.setVisibility(View.GONE);
				saveEventButton.setVisibility(View.GONE);
				editEventButton.setVisibility(View.GONE);
				viewParticipantButton.setVisibility(View.GONE);
			}
			for (EditText text : allEventEditTextViews) {
				disableEditText(text);
			}
		}

		startDateEditText.setInputType(InputType.TYPE_NULL);
		startTimeEditText.setInputType(InputType.TYPE_NULL);
		endTimeEditText.setInputType(InputType.TYPE_NULL);
		endDateEditText.setInputType(InputType.TYPE_NULL);
	}

	/**
	 * Opens a date picker for start date triggered by OnFocusChange
	 *
	 * @param hasFocus if user clicked on component
	 */
	@OnFocusChange(R.id.start_date_edit_text)
	public void showStartDatePicker(boolean hasFocus) {
		if (hasFocus) {
			DialogFragment newFragment = new DatePickerFragment();
			newFragment.show(getSupportFragmentManager(), DateTimePicker.START_DATE_PICKER.toString());
		}
	}

	/**
	 * Opens a date picker for end date triggered by OnFocusChange
	 *
	 * @param hasFocus if user clicked on component
	 */
	@OnFocusChange(R.id.end_date_edit_text)
	public void showEndDatePicker(boolean hasFocus) {
		if (hasFocus) {
			DialogFragment newFragment = new DatePickerFragment();
			newFragment.show(getSupportFragmentManager(), DateTimePicker.END_DATE_PICKER.toString());
		}
	}

	/**
	 * Opens a time picker for start time triggered by OnFocusChange
	 *
	 * @param hasFocus if user clicked on component
	 */
	@OnFocusChange(R.id.start_time_edit_text)
	public void showStartTimePicker(boolean hasFocus) {
		if (hasFocus) {
			DialogFragment newFragment = new TimePickerFragment();
			newFragment.show(getSupportFragmentManager(), DateTimePicker.START_TIME_PICKER.toString());
		}
	}

	/**
	 * Opens a time picker for end time triggered by OnFocusChange
	 *
	 * @param hasFocus if user clicked on component
	 */
	@OnFocusChange(R.id.end_time_edit_text)
	public void showEndTimePicker(boolean hasFocus) {
		if (hasFocus) {
			DialogFragment newFragment = new TimePickerFragment();
			newFragment.show(getSupportFragmentManager(), DateTimePicker.END_TIME_PICKER.toString());
		}
	}

	/**
	 * Creates the intent to open google maps based on String location
	 */
	@OnClick(R.id.location_row)
	public void openGoogleMaps() {
		String locationQuery = "geo:0,0?q=" + location;
		Uri gmmIntentUri = Uri.parse(locationQuery);
		// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
		// Make the Intent explicit by setting the Google Maps package
		mapIntent.setPackage("com.google.android.apps.maps");
		// Attempt to start an activity that can handle the Intent
		startActivity(mapIntent);
	}

	/**
	 * Makes the save button appear when button register event is clicked
	 */
	@OnClick(R.id.register_event_button)
	public void registerToEvent() {
		createUserEvent(false);
		checkInEventButton.setVisibility(View.VISIBLE);
		registerEventButton.setVisibility(View.GONE);
		// if (currentDate == startDate){
		checkInEventButton.setEnabled(true);
		// }
	}

	/**
	 * Calls the put method to userEvents to save changes to check in a user
	 * on click of checkIn button
	 */
	@OnClick(R.id.check_in_event_button)
	public void checkIntoEvent() {
		String url = BASE_URL + "/userEvents/" + userEvent.getUserEventId();
		try {
			JSONObject json = new JSONObject()
					.put("userId", userEvent.getUserId())
					.put("eventId", userEvent.getEventId())
					.put("isCheckedIn", true)
					.put("isAdmin", userEvent.getIsAdmin());

			Object res = APIHelper.makePUTRequest(url, json);
			if (res != null) {
				checkInEventButton.setVisibility(View.GONE);
				checkedInText.setVisibility(View.VISIBLE);
				openSuccessDialog();
				userEvent = new UserEvent(res.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
			String errorMsg = "Error: JSONException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * On successfully check in, show the success dialog
	 */
	public void openSuccessDialog() {
		SuccessDialog successDialog = new SuccessDialog();
		successDialog.show(getSupportFragmentManager(), "success dialog");
	}

	/**
	 * Enables all fields to be editable on click of the edit event
	 * button and shows the save button
	 */
	@OnClick(R.id.edit_event_button)
	public void editEvent() {
		// change button to `save` and enable all EditText fields
		createEventButton.setVisibility(View.GONE);
		saveEventButton.setVisibility(View.VISIBLE);
		editEventButton.setVisibility(View.GONE);
		toolbar.setTitle("Edit Event");

		for (EditText text : allEventEditTextViews) {
			enableEditText(text);
		}
	}

	/**
	 * Shows the registrant activity screen on click of the View Participant button
	 */
	@OnClick(R.id.view_participant_button)
	public void goToRegistrantScreen() {
		Intent i = new Intent(this, RegistrantInfoActivity.class);
		i.putExtra("event", event);
		startActivity(i);
	}

	/**
	 * Calls the put method of events to save new data of an event
	 * on click of the save button
	 */
	@OnClick(R.id.save_event_button)
	public void saveEvent() {
		if (!validateData()) {
			return;
		}
		String url = BASE_URL + "/events/" + event.getId();
		try {
			JSONObject json = new JSONObject()
					.put("name", eventName)
					.put("description", description)
					.put("location", location)
					.put("startDateTime", String.format("%sT%s", startDate, startTime))
					.put("endDateTime", String.format("%sT%s", endDate, endTime));

			Object res = APIHelper.makePUTRequest(url, json);
			if (res != null) {
				// save button to `edit` and disable all EditText fields
				saveEventButton.setVisibility(View.GONE);
				editEventButton.setVisibility(View.VISIBLE);
				for (EditText text : allEventEditTextViews) {
					disableEditText(text);
				}
				event = new Event(res.toString());
				toolbar.setTitle("Event Details");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			String errorMsg = "Error: JSONException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * Calls the post endpoint of events to save new event to database
	 * on click of the create event button
	 */
	@OnClick(R.id.create_event_button)
	public void createEvent() {
		if (!validateData()) {
			return;
		}

		String url = BASE_URL + "/events";
		try {
			JSONObject json = new JSONObject()
					.put("name", eventName)
					.put("description", description)
					.put("location", location)
					.put("startDateTime", String.format("%sT%s", startDate, startTime))
					.put("endDateTime", String.format("%sT%s", endDate, endTime));

			Object res = APIHelper.makePOSTRequest(url, json);
			if (res != null) {
				// change button to `edit` disable all EditText fields
				createEventButton.setVisibility(View.GONE);
				editEventButton.setVisibility(View.VISIBLE);
				viewParticipantButton.setVisibility(View.VISIBLE);
				for (EditText text : allEventEditTextViews) {
					disableEditText(text);
				}
				// set global Event variable
				event = new Event(res.toString());
				// creates UserEvent relation with user isAdmin = true
				createUserEvent(true);
				SocketHelper.sendMessage(event.getName());
				toolbar.setTitle("Event Details");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			String errorMsg = "Error: JSONException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * sets the fields of the event details page to the data collected
	 * from the event passed through from the other activity
	 */
	private void setEventFields() {
		nameEditText.setText(event.getName());
		locationEditText.setText(event.getLocation());
		descriptionEditText.setText(event.getDescription());
		startDateEditText.setText(event.getDateString(event.getStartDateTime()));
		endDateEditText.setText(event.getDateString(event.getEndDateTime()));
		endTimeEditText.setText(event.getTimeString(event.getEndDateTime()));
		startTimeEditText.setText(event.getTimeString(event.getStartDateTime()));
	}

	/**
	 * Checks valid event data to be passed to backend
	 *
	 * @return true if data valid, else false
	 */
	public boolean validateData() {
		getCurrentData();
		if (eventName.length() < 1 || eventName.length() > 100) {
			Toast.makeText(getApplicationContext(), "Enter a valid name", Toast.LENGTH_LONG).show();
			return false;
		} else if (location.length() < 1 || location.length() > 100) {
			Toast.makeText(getApplicationContext(), "Enter a valid location", Toast.LENGTH_LONG).show();
			return false;
		} else if (description.length() < 1 || description.length() > 1000) {
			Toast.makeText(getApplicationContext(), "Enter valid description", Toast.LENGTH_LONG).show();
			return false;
		} else if (startDate.length() < 1) {
			Toast.makeText(getApplicationContext(), "Enter a start date", Toast.LENGTH_LONG).show();
			return false;
		} else if (endDate.length() < 1) {
			Toast.makeText(getApplicationContext(), "Enter a end date", Toast.LENGTH_LONG).show();
			return false;
		} else if (startTime.length() < 1) {
			Toast.makeText(getApplicationContext(), "Enter a start time", Toast.LENGTH_LONG).show();
			return false;
		} else if (endTime.length() < 1) {
			Toast.makeText(getApplicationContext(), "Enter a end time", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	/**
	 * Initialize all the global variable values
	 */
	public void getCurrentData() {
		eventName = nameEditText.getText().toString();
		location = locationEditText.getText().toString();
		description = descriptionEditText.getText().toString();
		startDate = startDateEditText.getText().toString();
		endDate = endDateEditText.getText().toString();
		startTime = startTimeEditText.getText().toString();
		endTime = endTimeEditText.getText().toString();
	}

	/**
	 * Disable all EditText fields
	 *
	 * @param editText EditText field to disable
	 */
	private void disableEditText(EditText editText) {
		editText.setFocusable(false);
		editText.setEnabled(false);
		editText.setCursorVisible(false);
		for (View view : allEventViews) {
			view.setVisibility(View.GONE);
		}
	}

	/**
	 * Enable EditText field
	 *
	 * @param editText EditText file to enable
	 */
	private void enableEditText(EditText editText) {
		editText.setFocusableInTouchMode(true);
		editText.setEnabled(true);
		editText.setCursorVisible(true);
		for (View view : allEventViews) {
			view.setVisibility(View.VISIBLE);
		}
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
			// set global variable user
			user = new User(res.toString());
		}
	}

	/**
	 * Calls get method of userEvents endpoint to get the data
	 * for the user/event relationship for the current page
	 */
	private void fetchUserEvent() {
		String url = String.format(
				"%s/userEvents/users/%s/events/%s",
				BASE_URL,
				user.getId(),
				event.getId()
		);
		Object res = APIHelper.makeGETRequest(url);
		if (res != null) {
			// set global userEvent variable
			userEvent = new UserEvent(res.toString());
		}
	}

	/**
	 * Calls the post method of userEvents to create a relationship
	 * between user and event
	 *
	 * @param isAdmin if the user is admin of the event or not
	 */
	public void createUserEvent(boolean isAdmin) {
		String url = String.format(
				"%s/userEvents/users/%s/events/%s/%s",
				BASE_URL,
				user.getId(),
				event.getId(),
				isAdmin
		);

		JSONObject json = new JSONObject();
		Object res = APIHelper.makePOSTRequest(url, json);
		if (res != null) {
			// set userEvent global variable
			userEvent = new UserEvent(res.toString());
		}
	}
}

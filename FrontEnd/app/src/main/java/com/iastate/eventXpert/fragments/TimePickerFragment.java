package com.iastate.eventXpert.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.iastate.eventXpert.R;
import com.iastate.eventXpert.enums.DateTimePicker;

import java.util.Calendar;

/**
 * The TimePicker that allows the user to chose a time from a 24 hour clock
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
	/**
	 * Actions that should take place when the dialog is created
	 * Sets the initial hour and minute to the current time
	 *
	 * @param savedInstanceState A bundle of the saved instance state
	 *
	 * @return the TimePickerDialog that was created
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default time in the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute, true);
	}

	/**
	 * When the time is selected it formats it (hh:mm) and sets it on the Event
	 *
	 * @param view   The TimePicker that was used when selecting the time
	 * @param hour   the hour that the user selected
	 * @param minute the minute that the user selected
	 */
	public void onTimeSet(TimePicker view, int hour, int minute) {
		String tag = getTag();
		DateTimePicker pickerType = DateTimePicker.valueOf(tag);
		if (pickerType == DateTimePicker.START_TIME_PICKER) {
			EditText startTimeEditText = getActivity().findViewById(R.id.start_time_edit_text);
			startTimeEditText.setText(String.format("%02d:%02d", hour, minute));
		} else if (pickerType == DateTimePicker.END_TIME_PICKER) {
			EditText endTimeEditText = getActivity().findViewById(R.id.end_time_edit_text);
			endTimeEditText.setText(String.format("%02d:%02d", hour, minute));
		}
	}
}

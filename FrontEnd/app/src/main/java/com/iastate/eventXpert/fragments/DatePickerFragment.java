package com.iastate.eventXpert.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.iastate.eventXpert.R;
import com.iastate.eventXpert.enums.DateTimePicker;

import java.util.Calendar;

/**
 * A fragment which allows a user to pick a date from a calendar
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	/**
	 * Actions that should take place when the dialog is created
	 * Sets the initial year, month, and day to the current date
	 *
	 * @param savedInstanceState A bundle of the saved instance state
	 *
	 * @return the DatePickerDialog that was created
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	/**
	 * When the date is chosen it formats the string correctly (yyyy-mm-dd) and saves it to the event
	 *
	 * @param view  the DatePicker that was used
	 * @param year  the selected year
	 * @param month the selected month
	 * @param day   the selected day
	 */
	public void onDateSet(DatePicker view, int year, int month, int day) {
		String tag = getTag();
		DateTimePicker pickerType = DateTimePicker.valueOf(tag);
		if (pickerType == DateTimePicker.START_DATE_PICKER) {
			EditText startDateEditText = getActivity().findViewById(R.id.start_date_edit_text);
			startDateEditText.setText(String.format("%04d-%02d-%02d", year, month + 1, day));
		} else if (pickerType == DateTimePicker.END_DATE_PICKER) {
			EditText endDateEditText = getActivity().findViewById(R.id.end_date_edit_text);
			endDateEditText.setText(String.format("%04d-%02d-%02d", year, month + 1, day));
		}
	}
}

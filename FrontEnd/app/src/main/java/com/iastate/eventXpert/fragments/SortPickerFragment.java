package com.iastate.eventXpert.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.iastate.eventXpert.activities.FindEventsActivity;

public class SortPickerFragment extends DialogFragment {
	private static String selectedOption;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		CharSequence[] items = {"location", "startDateTime", "endDateTime", "description", "none"};
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Sort By:")
				.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectedOption = items[which].toString();
					}
				})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

		//Set single choice items
		return builder.create();
	}

	public static String getSelectedOption() {
		return selectedOption;
	}

	@Override
	public void onDismiss(@NonNull DialogInterface dialog) {
		FindEventsActivity thisActivity = (FindEventsActivity) getActivity();
		thisActivity.queryTextSubmit();
	}

}

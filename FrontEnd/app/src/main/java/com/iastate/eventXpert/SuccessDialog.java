package com.iastate.eventXpert;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * Create a dialog for a successfully checking in
 */
public class SuccessDialog extends AppCompatDialogFragment {

	/**
	 * Creates a dialog box for successfully checking into an event
	 *
	 * @param savedInstanceState state of the activity
	 *
	 * @return a dialog box
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Notification")
				.setMessage("Successfully checked into the event!")
				.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {

					}
				});
		return builder.create();
	}
}

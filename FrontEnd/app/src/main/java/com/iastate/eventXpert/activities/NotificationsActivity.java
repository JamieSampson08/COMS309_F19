package com.iastate.eventXpert.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.iastate.eventXpert.R;
import com.iastate.eventXpert.helpers.NavigationHelper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class NotificationsActivity extends AppCompatActivity {
	TextView notificationText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notifications);
		notificationText = findViewById(R.id.notificationText);
		Toolbar toolbar = findViewById(R.id.toolbar);
		NavigationHelper.createDrawer(this, toolbar);
		readFile();
	}

	public void readFile() {
		try {
			FileInputStream fileInputStream = openFileInput("Notifications.txt");
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			StringBuffer stringBuffer = new StringBuffer();

			String lines;
			while ((lines = bufferedReader.readLine()) != null) {
				stringBuffer.insert(0, lines + "\n");
			}
			if (stringBuffer.length() != 0) {
				notificationText.setText(stringBuffer.toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

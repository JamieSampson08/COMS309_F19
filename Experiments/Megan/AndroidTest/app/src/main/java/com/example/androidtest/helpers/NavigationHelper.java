package com.example.androidtest.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.example.androidtest.MainActivity;
import com.example.androidtest.PersonalizationActivity;
import com.example.androidtest.R;

public class NavigationHelper {
	public static boolean onNavigationItemSelected(MenuItem item, Activity activity) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		System.out.println("id: " + id);

		if (id == R.id.nav_home) {
			// Handle the camera action
			System.out.println("Home");
			Intent myIntent = new Intent(activity.getBaseContext(), MainActivity.class);
			activity.startActivityForResult(myIntent, 0);
		} else if (id == R.id.nav_personalize) {
			System.out.println("Personalize");
			Intent myIntent = new Intent(activity.getBaseContext(), PersonalizationActivity.class);
			activity.startActivityForResult(myIntent, 0);
		}

		DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}

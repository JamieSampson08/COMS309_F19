package com.iastate.eventXpert.helpers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.iastate.eventXpert.activities.FindEventsActivity;
import com.iastate.eventXpert.activities.MainActivity;
import com.iastate.eventXpert.activities.ManageEventsActivity;
import com.iastate.eventXpert.activities.NotificationsActivity;
import com.iastate.eventXpert.R;
import com.iastate.eventXpert.activities.SignInActivity;
import com.iastate.eventXpert.activities.UserDetailsActivity;
import com.iastate.eventXpert.objects.SignedInUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.iastate.eventXpert.constants.NavigationConstants.*;

public class NavigationHelper {

	public static void createDrawer(Activity activity, Toolbar toolbar) {
		SignedInUser signedInUser = SignedInUser.getSignedInUser();
		AccountHeader accountHeader = new AccountHeaderBuilder()
				.withActivity(activity)
				.withHeaderBackground(R.drawable.header)
				.addProfiles(
						new ProfileDrawerItem().withName(signedInUser.getName()).withEmail(signedInUser.getEmail()).withIcon(activity.getResources().getDrawable(R.drawable.cat_avatar))
				)
				.build();

		Drawer drawer = new DrawerBuilder()
				.withActivity(activity)
				.withToolbar(toolbar)
				.withAccountHeader(accountHeader)
				.addDrawerItems(
						new PrimaryDrawerItem().withIdentifier(HOME_PAGE_ID).withName(R.string.nav_home),
						new PrimaryDrawerItem().withIdentifier(SETTINGS_PAGE_ID).withName(R.string.nav_settings),
						new PrimaryDrawerItem().withIdentifier(FIND_EVENTS_PAGE_ID).withName(R.string.nav_find_events),
						new PrimaryDrawerItem().withIdentifier(MANAGE_EVENTS_PAGE_ID).withName(R.string.nav_manage_events),
						new PrimaryDrawerItem().withIdentifier(NOTIFICATIONS_PAGE_ID).withName(R.string.nav_notifications),
						new PrimaryDrawerItem().withIdentifier(LOGIN_PAGE_ID).withName(R.string.sign_out_page)
				)
				.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
					@Override
					public boolean onItemClick(@Nullable View view, int i, @NotNull IDrawerItem<?> iDrawerItem) {
						NavigationHelper.onItemClick(iDrawerItem, activity);
						return false;
					}
				})
				.build();
		drawer.setSelection(-1);
	}

	public static void onItemClick(@NotNull IDrawerItem<?> iDrawerItem, Activity activity) {
		long identifier = iDrawerItem.getIdentifier();
		if (identifier == HOME_PAGE_ID) {
			Intent myIntent = new Intent(activity.getBaseContext(), MainActivity.class);
			activity.startActivityForResult(myIntent, 0);
		} else if (identifier == SETTINGS_PAGE_ID) {
			Intent myIntent = new Intent(activity.getBaseContext(), UserDetailsActivity.class);
			activity.startActivityForResult(myIntent, 0);
		} else if (identifier == FIND_EVENTS_PAGE_ID) {
			Intent myIntent = new Intent(activity.getBaseContext(), FindEventsActivity.class);
			activity.startActivityForResult(myIntent, 0);
		} else if (identifier == MANAGE_EVENTS_PAGE_ID) {
			Intent myIntent = new Intent(activity.getBaseContext(), ManageEventsActivity.class);
			activity.startActivityForResult(myIntent, 0);
		} else if (identifier == NOTIFICATIONS_PAGE_ID) {
			Intent myIntent = new Intent(activity.getBaseContext(), NotificationsActivity.class);
			activity.startActivityForResult(myIntent, 0);
		} else if (identifier == LOGIN_PAGE_ID) {
			Intent myIntent = new Intent(activity.getBaseContext(), SignInActivity.class);
			activity.startActivityForResult(myIntent, 0);
		}
	}
}

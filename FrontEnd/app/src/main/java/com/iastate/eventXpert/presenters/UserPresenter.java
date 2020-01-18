package com.iastate.eventXpert.presenters;

import android.widget.Toast;

import com.iastate.eventXpert.activities.SignInActivity;
import com.iastate.eventXpert.helpers.APIHelper;
import com.iastate.eventXpert.interfaces.UserView;
import com.iastate.eventXpert.objects.Mastery;
import com.iastate.eventXpert.objects.User;

import org.json.JSONException;
import org.json.JSONObject;

import static com.iastate.eventXpert.constants.UrlConstants.BASE_MASTERY_IMAGE_URL;
import static com.iastate.eventXpert.constants.UrlConstants.BASE_URL;
import static com.mikepenz.iconics.Iconics.getApplicationContext;

public class UserPresenter {
	private User user;
	private UserView view;

	public UserPresenter(UserView view) {
		this.view = view;
		fetchUser();
		view.updateUser(user);
		getMastery();
		view.disableEditing(true);
	}

	public UserPresenter(UserView view, User user) {
		this.view = view;
		this.user = user;
		view.updateUser(user);
		getMastery();
		view.disableEditing(user.getId().equals(SignInActivity.sessionUserId));
	}

	public User getUser() {
		return user;
	}

	private void fetchUser() {
		String url = String.format("%s/users/%s", BASE_URL, SignInActivity.sessionUserId);
		Object res = APIHelper.makeGETRequest(url);

		if (res != null) {
			user = new User(res.toString());
		}
	}

	private void getMastery() {
		String url = String.format("%s/masteries?points=%s", BASE_URL, user.getPoints());

		Object res = APIHelper.makeGETRequest(url);

		if (res != null) {
			Mastery mastery = new Mastery(res.toString());
			user.setMastery(mastery);
			view.setMasteryText(mastery.getName());
			view.setMasteryImage(BASE_MASTERY_IMAGE_URL + mastery.getName() + ".png");
		}
	}

	public void save(String firstName, String lastName) {
		if (!validateData(firstName, lastName)) {
			return;
		}
		String url = BASE_URL + "/users/" + user.getId();
		try {
			JSONObject json = new JSONObject();
			json.put("firstName", firstName);
			json.put("lastName", lastName);
			json.put("email", user.getEmail());
			json.put("profileFileName", user.getProfileFileName());

			Object res = APIHelper.makePUTRequest(url, json);
			if (res != null) {
				user = new User(res.toString());
				view.disableEditing(true);
				view.updateUser(user);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			String errorMsg = "Error: JSONException";
			Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		}
	}

	private boolean validateData(String firstName, String lastName) {
		if (lastName.length() < 1 && lastName.length() < 100) {
			Toast.makeText(getApplicationContext(), "Enter valid last name", Toast.LENGTH_LONG).show();
			return false;
		} else if (firstName.length() < 1 && firstName.length() < 100) {
			Toast.makeText(getApplicationContext(), "Enter valid first name", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
}

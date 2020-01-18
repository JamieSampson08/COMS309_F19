package com.iastate.eventXpert.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iastate.eventXpert.R;
import com.iastate.eventXpert.helpers.NavigationHelper;
import com.iastate.eventXpert.interfaces.UserView;
import com.iastate.eventXpert.objects.User;
import com.iastate.eventXpert.presenters.UserPresenter;


import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.iastate.eventXpert.constants.UrlConstants.*;

/**
 * An Activity which controls the UserSettings Page.
 * On the user Settings page Users are able to create a new User.
 */
public class UserDetailsActivity extends AppCompatActivity implements UserView {
	private UserPresenter presenter;

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.avatarImage)
	ImageView avatarImage;

	@BindView(R.id.edit_profile_picture)
	FloatingActionButton editProfilePicture;

	@BindView(R.id.profile_image_view)
	ImageView profileImageView;

	@BindView(R.id.mastery_image)
	ImageView masteryImage;

	@BindView(R.id.firstNameEditText)
	EditText firstNameEditText;

	@BindView(R.id.lastNameEditText)
	EditText lastNameEditText;

	@BindViews({
			R.id.lastNameEditText,
			R.id.firstNameEditText
	})
	List<EditText> allUserEditTextViews;

	@BindView(R.id.firstNameText)
	TextView firstNameText;

	@BindView(R.id.lastNameText)
	TextView lastNameText;

	@BindViews({
			R.id.lastNameText,
			R.id.firstNameText
	})
	List<TextView> allUserTextViews;

	@BindView(R.id.mastery_text)
	TextView masteryText;

	@BindView(R.id.points_text)
	TextView pointsEditText;

	@BindView(R.id.emailText)
	TextView emailText;

	@BindView(R.id.saveButton)
	Button saveButton;

	@BindView(R.id.edit_user_button)
	Button editUserButton;

	/**
	 * The instructions that should be executed when the screen is created
	 * Primarily includes setting up default behavior
	 *
	 * @param savedInstanceState a Bundle of the saved instance state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_details);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		NavigationHelper.createDrawer(this, toolbar);

		// if the user was passed through intent, set all values to given user values
		User user = (User) getIntent().getSerializableExtra("user");
		if (user != null) {
			presenter = new UserPresenter(this, user);
		} else {
			presenter = new UserPresenter(this);
		}
	}

	//////////////// Handle button clicks

	@OnClick(R.id.edit_user_button)
	public void editUser() {
		enableEditing();
	}

	@OnClick(R.id.edit_profile_picture)
	public void changeProfilePicture() {
		Intent uploadImageIntent = new Intent(this, UploadImageActivity.class);
		uploadImageIntent.putExtra("user", presenter.getUser());
		startActivity(uploadImageIntent);
	}

	@OnClick(R.id.saveButton)
	public void onClick(View view) {
		presenter.save(firstNameEditText.getText().toString(), lastNameEditText.getText().toString());
	}

	/////////// UserView methods

	@Override
	public void setMasteryText(String masteryLevel) {
		masteryText.setText(masteryLevel);
	}

	@Override
	public void setMasteryImage(String imageName) {
		Glide.with(this).load(imageName).into(masteryImage);
	}

	@Override
	public void updateUser(User user) {
		emailText.setText(user.getEmail());
		firstNameEditText.setText(user.getFirstName());
		firstNameText.setText(user.getFirstName());
		lastNameEditText.setText(user.getLastName());
		lastNameText.setText(user.getLastName());
		pointsEditText.setText("" + user.getPoints());
		String path = BASE_PROFILE_IMAGE_URL + user.getProfileFileName();
		Glide.with(this).load(path).into(profileImageView);
	}

	@Override
	public void enableEditing() {
		saveButton.setVisibility(View.VISIBLE);
		editUserButton.setVisibility(View.GONE);
		for (EditText text : allUserEditTextViews) {
			text.setVisibility(View.VISIBLE);
		}
		for (TextView text : allUserTextViews) {
			text.setVisibility(View.GONE);
		}
		toolbar.setTitle("Edit User");
	}

	@Override
	public void disableEditing(boolean showEditButton) {
		saveButton.setVisibility(View.GONE);
		if (showEditButton) {
			editUserButton.setVisibility(View.VISIBLE);
		} else {
			editProfilePicture.setVisibility(View.GONE);
			editUserButton.setVisibility(View.GONE);
		}
		for (EditText text : allUserEditTextViews) {
			text.setVisibility(View.GONE);
		}
		for (TextView text : allUserTextViews) {
			text.setVisibility(View.VISIBLE);
		}
		toolbar.setTitle("User Details");
	}
}

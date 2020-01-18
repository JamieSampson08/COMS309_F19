package com.iastate.eventXpert.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.iastate.eventXpert.R;
import com.iastate.eventXpert.helpers.APIHelper;
import com.iastate.eventXpert.helpers.NavigationHelper;
import com.iastate.eventXpert.objects.SignedInUser;
import com.iastate.eventXpert.objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.iastate.eventXpert.constants.UrlConstants.BASE_URL;

public class SignInActivity extends AppCompatActivity {

	public static Integer sessionUserId;
	private static final String TAG = "SignInActivity";
	private static final int RC_SIGN_IN = 9001;

	private GoogleSignInClient mGoogleSignInClient;

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.status)
	TextView mStatusTextView;

	@BindView(R.id.sign_out_button)
	Button signOutButton;

	@BindView(R.id.sign_in_button)
	SignInButton signInButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);

		setSupportActionBar(toolbar);
		NavigationHelper.createDrawer(this, toolbar);

		// Configure sign-in to request the user's ID, email address, and basic
		// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken(getString(R.string.server_client_id))
				.requestEmail()
				.build();

		// Build a GoogleSignInClient with the options specified by gso.
		mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

		// Set the dimensions of the sign-in button.
		signInButton.setSize(SignInButton.SIZE_STANDARD);
		signInButton.setColorScheme(SignInButton.COLOR_LIGHT);
	}

	@Override
	public void onStart() {
		super.onStart();

		// Check for existing Google Sign In account, if the user is already signed in
		// the GoogleSignInAccount will be non-null.
		GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
		updateUI(account);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			// The Task returned from this call is always completed, no need to attach
			// a listener.
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			handleSignInResult(task);
		}
	}

	private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
		try {

			GoogleSignInAccount account = completedTask.getResult(ApiException.class);

			// Signed in successfully, show authenticated UI.
			updateUI(account);
		} catch (ApiException e) {
			// The ApiException status code indicates the detailed failure reason.
			// Please refer to the GoogleSignInStatusCodes class reference for more information.
			Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
			updateUI(null);
		}
	}

	@OnClick(R.id.sign_in_button)
	public void signIn() {
		Intent signInIntent = mGoogleSignInClient.getSignInIntent();
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}

	@OnClick(R.id.sign_out_button)
	public void signOut() {
		mGoogleSignInClient.signOut()
				.addOnCompleteListener(this, new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						updateUI(null);

					}
				});
	}

	private void updateUI(@Nullable GoogleSignInAccount account) {
		if (account != null) {
			mStatusTextView.setText(getString(R.string.signed_in_fmt, account.getDisplayName()));

			sessionUserId = getIdFromEmail(account.getEmail());

			//CREATE A NEW USER
			if (sessionUserId == -1 || sessionUserId == null) {
				String url = BASE_URL + "/users";
				try {
					JSONObject json = new JSONObject();
					json.put("firstName", account.getGivenName());
					json.put("lastName", account.getFamilyName().toString());
					json.put("email", account.getEmail().toString());

					Object res = APIHelper.makePOSTRequest(url, json);
					if (res != null) {
						User user = new User(res.toString());
						sessionUserId = user.getId();
						SignedInUser.getSignedInUser().setUser(user);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					String errorMsg = "Error: JSONException";
					Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
				}
			}
			if (sessionUserId != -1) {
				String url = String.format("%s/users/%s", BASE_URL, SignInActivity.sessionUserId);
				Object res = APIHelper.makeGETRequest(url);
				if (res != null) {
					User user = new User(res.toString());
					sessionUserId = user.getId();
					SignedInUser.getSignedInUser().setUser(user);
				}
				toolbar.setVisibility(View.VISIBLE);
				signInButton.setVisibility(View.GONE);
				signOutButton.setVisibility(View.VISIBLE);
			}
		} else {
			mStatusTextView.setText(R.string.signed_out);
			sessionUserId = -1;
			toolbar.setVisibility(View.GONE);
			signInButton.setVisibility(View.VISIBLE);
			signOutButton.setVisibility(View.GONE);
		}
	}

	private Integer getIdFromEmail(String email) {
		String url = BASE_URL + "/users/?email=" + email;

		ArrayList<User> allUsers;
		Object res = APIHelper.makeGETRequest(url);
		JSONArray arrUser = null;
		try {
			arrUser = new JSONArray(res.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		allUsers = new ArrayList<User>();

		int i = 0;

		while (!(arrUser.isNull(i))) {
			String temp = null;
			try {
				temp = arrUser.getString(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			allUsers.add(new User(temp));
			i++;
		}

		if (allUsers.size() == 0) {
			return -1;
		}

		return allUsers.get(0).getId();
	}

}

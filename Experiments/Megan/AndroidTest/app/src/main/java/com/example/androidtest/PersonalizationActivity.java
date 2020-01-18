package com.example.androidtest;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidtest.helpers.NavigationHelper;
import com.example.androidtest.httpRequests.OkHttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.example.androidtest.MainActivity.IP_ADDRESS;


public class PersonalizationActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	EditText nameEditText;
	EditText idEditText;
	Button searchButton;
	Button updateButton;
	Button addButton;
	TextView resultTextView;
	TextView errorTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personalization);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		resultTextView = findViewById(R.id.personalization_text);
		errorTextView = findViewById(R.id.personalize_error_text);
		nameEditText = findViewById(R.id.name_edit_text);
		idEditText = findViewById(R.id.id_edit_text);
		searchButton = findViewById(R.id.search_by_id);
		updateButton = findViewById(R.id.modify_by_id);
		addButton = findViewById(R.id.add_new);
		searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onSearchButtonPressed();
			}
		});
		addButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				onAddButtonPressed();
			}
		});
		updateButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				onModifyButtonPressed();
			}
		});
		NavigationView navigationView = findViewById(R.id.nav_view);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		String url = "http://" + IP_ADDRESS + ":8080/greeting";
		OkHttpHandler okHttpHandler= new OkHttpHandler();
		try {
			Object response = okHttpHandler.execute(url).get();
			System.out.println("response: " + response);
			resultTextView.setText(response.toString());
		} catch (ExecutionException e) {
			e.printStackTrace();
			resultTextView.setText(R.string.error_string);
		} catch (InterruptedException e) {
			e.printStackTrace();
			resultTextView.setText(R.string.error_string);
		}
		navigationView.setNavigationItemSelectedListener(this);
	}

	private void onSearchButtonPressed() {
		String id = idEditText.getText().toString();
		if(id.equals("")){
			errorTextView.setText(R.string.no_id_error);
		}

		String url = "http://" + IP_ADDRESS + ":8080/greeting/" + id;

		try {
			OkHttpHandler okHttpHandler = new OkHttpHandler();
			Object response = okHttpHandler.execute(url).get();
			System.out.println("response: " + response);
			resultTextView.setText(response.toString());
		} catch (ExecutionException e) {
			e.printStackTrace();
			resultTextView.setText(R.string.error_string);
		} catch (InterruptedException e) {
			e.printStackTrace();
			resultTextView.setText(R.string.error_string);
		}
	}

	private void onModifyButtonPressed() {
		String name = nameEditText.getText().toString();
		String id = idEditText.getText().toString();
		if(id.equals("") || name.equals("")) {
			errorTextView.setText(R.string.both_required_error);
		}

		String url = "http://" + IP_ADDRESS + ":8080/greeting/" + id;

		try{
			OkHttpHandler okHttpHandler = new OkHttpHandler();
			JSONObject json = new JSONObject().put("name", name);
			RequestBody body = RequestBody.create(JSON, json.toString());

			Request request = new Request.Builder()
					.url(url)
					.put(body)
					.build();
			Object response = okHttpHandler.execute(request).get();
			System.out.println("response: " + response);
			System.out.println("response.toString(): " + response.toString());
			resultTextView.setText(response.toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
			resultTextView.setText(R.string.error_string);
		} catch (ExecutionException e) {
			e.printStackTrace();
			resultTextView.setText(R.string.error_string);
		} catch (JSONException e) {
			e.printStackTrace();
			resultTextView.setText(R.string.error_string);
		}
	}

	private void onAddButtonPressed() {
		String name = nameEditText.getText().toString();
		if(name.equals("")){
			errorTextView.setText(R.string.no_name_error);
		}

		String url = "http://" + IP_ADDRESS + ":8080/greeting";
		try{
			OkHttpHandler okHttpHandler = new OkHttpHandler();
			JSONObject json = new JSONObject().put("name", name);
			RequestBody body = RequestBody.create(JSON, json.toString());

			Request request = new Request.Builder()
					.url(url)
					.post(body)
					.build();
			 Object response = okHttpHandler.execute(request).get();
			 System.out.println("response: " + response);
			 System.out.println("response.toString(): " + response.toString());
			 resultTextView.setText(response.toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
			resultTextView.setText(R.string.error_string);
		} catch (ExecutionException e) {
			e.printStackTrace();
			resultTextView.setText(R.string.error_string);
		} catch (JSONException e) {
			e.printStackTrace();
			resultTextView.setText(R.string.error_string);
		}
	}


	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personalization, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		return NavigationHelper.onNavigationItemSelected(item, this);
	}
}

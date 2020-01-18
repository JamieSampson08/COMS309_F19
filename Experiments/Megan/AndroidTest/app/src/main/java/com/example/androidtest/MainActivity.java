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
import android.widget.TextView;
import com.example.androidtest.helpers.NavigationHelper;
import com.example.androidtest.httpRequests.OkHttpHandler;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private TextView textView;
	public final static String IP_ADDRESS = "10.26.28.77";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		textView = findViewById(R.id.main_text_view);
		String url = "http://" + IP_ADDRESS + ":8080/greeting";
		OkHttpHandler okHttpHandler= new OkHttpHandler();
		try {
			Object response = okHttpHandler.execute(url).get();
			System.out.println("response: " + response);
			textView.setText(response.toString());
		} catch (ExecutionException e) {
			e.printStackTrace();
			textView.setText(R.string.error_string);
		} catch (InterruptedException e) {
			e.printStackTrace();
			textView.setText(R.string.error_string);
		}
		navigationView.setNavigationItemSelectedListener(this);
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
		getMenuInflater().inflate(R.menu.main, menu);
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

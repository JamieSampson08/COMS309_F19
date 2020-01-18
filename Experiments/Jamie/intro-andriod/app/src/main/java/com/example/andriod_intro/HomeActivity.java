package com.example.andriod_intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {
    TextView welcomeText;
    Button backButton;
    TextView postData;
    TextView getData;
    Button postButton;
    Button getButton;
    public String getUrl = "https://localhost:8080/get";
    //public String postUrl = "https://localhost:8080/post";
    // user for testing purposes
    public String postUrl = "https://reqres.in/api/users/";
    // used for testing purposes
    public String url= "https://reqres.in/api/users/2";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    // used for testing purposes
    public String postBody="{\n" +
            "    \"name\": \"morpheus\",\n" +
            "    \"job\": \"leader\"\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String nameText = intent.getStringExtra("nameText");
        String emailText = intent.getStringExtra("emailText");
        System.out.println(nameText);
        System.out.println(emailText);

        welcomeText = findViewById(R.id.welcomeText);
        postData = findViewById(R.id.postData);
        getData = findViewById(R.id.getData);
        backButton = (Button) findViewById(R.id.backButton);
        postButton = (Button) findViewById(R.id.postButton);

        String message = "Hello " + nameText + "!";
        System.out.print(message);
        welcomeText.setText(message);

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //String postBody = "name: " + name + "\nemail: " +  email;
                try {
                    postRequest(postUrl, postBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Doesn't work
//        getButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                OkHttpHandler okHttpHandler = new OkHttpHandler();
//                okHttpHandler.execute(url);
//            }
//        });
    }


    void postRequest(String postUrl, String postBody) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "Post Request Failed", Toast.LENGTH_LONG).show();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d("TAG",myResponse);

                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            JSONObject json = new JSONObject(myResponse);
                            System.out.println(json);
                            postData.setText(json.getString("name")+ " "+json.getString("job"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}

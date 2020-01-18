package com.example.hello_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.buttonMessage);
        b2 = (Button) findViewById(R.id.buttonNext);

        b1.setOnClickListner(new View.OnClickListener() {
            @Override
            public void OnClick(View view){
                Toast.makeText(getApplicationContext(), resId: "Hello Android!", Toast.LENGTH_LONG).show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}

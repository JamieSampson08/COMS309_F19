package com.example.andriod_intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button nextButton, toastButton;
    EditText nameText, emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = (Button) findViewById(R.id.buttonNext);
        toastButton = (Button) findViewById(R.id.buttonToast);

        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);

        toastButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (nameText.length() < 1){
                    Toast.makeText(getApplicationContext(), "Enter valid name", Toast.LENGTH_LONG).show();
                }
                else {
                    String message = "Hello " + nameText.getText();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               if (nameText.length() < 1 || emailText.length() < 1){
                   Toast.makeText(getApplicationContext(), "Enter valid information", Toast.LENGTH_LONG).show();
               }
               else {
                   Intent i = new Intent(MainActivity.this, HomeActivity.class);
                   String nameVariable = nameText.getText().toString();
                   System.out.println(nameVariable);
                   System.out.println(nameText.getText());
                   System.out.println(emailText.getText());
                   i.putExtra("nameText", nameText.getText().toString());
                   i.putExtra("emailText", emailText.getText().toString());
                   startActivity(i);
               }
           }
        });


    }
}

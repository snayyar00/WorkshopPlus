package com.example;
/**
 * Login page for Workshop+
 * Functionality: Toggle between Student/Educator accounts, toggle language.
 * Takes user input for email/password and proceeds to Subjects page.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginAttempt(View v){

        // Retrieving input from email textbox
        TextView username = findViewById(R.id.emailText);
        String user = username.getText().toString();
        Log.d("Username", user);

        // Retrieving input from password textbox
        TextView password = findViewById(R.id.passwordText);
        String pass = password.getText().toString();
                Log.d("Password", pass);
        Toast.makeText(this, "Logging in", Toast.LENGTH_LONG).show();
        // Todo: Retrieve account type from toggle menu
        // Todo: Verify username and password
        // Alert for incorrect login info
        // Go to next screen for correct login information
        String student = findViewById(R.id.student)
        if(student == "1")
        {
            Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        }
        else
        {
        Intent i = new Intent(this, SubjectsActivity.class);
        startActivity(i);
        }
    }

    // Alert for incorrect login

}

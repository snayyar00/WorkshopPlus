package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Success", "Activity created");
    }

    // Takes input from 
    public void handleInput(View v){
    
        // Retrieving input from email textbox
        TextView username = findViewById(R.id.emailText);
        String user = username.getText().toString();
        Log.d("Username", user);
        
        // Retrieving input from password textbox
        TextView password = findViewById(R.id.passwordText);
        String pass = password.getText().toString();
        Log.d("Password", pass);
        
        // todo: Retrieve account type from toggle menu
    }
}

package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AssignmentsActivity extends AppCompatActivity {
    String[] assignments = { "Assignment 1", "Assignment 2", "Code an Android app in 1 week"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        Intent i = getIntent();
        String course = i.getStringExtra("Course");
        ((TextView)findViewById(R.id.course)).setText(course);
        Spinner spin = (Spinner) findViewById(R.id.assnSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, assignments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }
    public void selectAssignment(View v){
        Spinner course = (Spinner)findViewById(R.id.assnSpinner);
        String assnID = course.getSelectedItem().toString();
        Log.d("Assignment ID", assnID);
        Toast.makeText(this, "Selected assignment: ", Toast.LENGTH_LONG).show();
        Toast.makeText(this, assnID, Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, VideoSelectActivity.class);
        i.putExtra("Assignment", assnID);
        startActivity(i);
    }
    public void goToSettings(View v){
        Intent i = new Intent(this,SettingsActivity.class);
        startActivity(i);
    }
}
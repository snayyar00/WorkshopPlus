package com.example.workshop;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AssignmentTAActivity extends AppCompatActivity {

    public GlobalClass globalClass;
    DatabaseReference db;
    ArrayList<String> arrayList = new ArrayList<>();
    Spinner spin;

    // Hardcoded example assignments
    //String[] assignments = { "Assignment 1", "Assignment 2", "Code an Android app in 1 week"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_t_a);

        globalClass = (GlobalClass)getApplicationContext();
        db = FirebaseDatabase.getInstance().getReference();

        Intent i = getIntent();
        String course = i.getStringExtra("Course");
        ((TextView)findViewById(R.id.course)).setText(course);
        spin = (Spinner) findViewById(R.id.assnSpinner);
        showDataSpinner();
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, assignments);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spin.setAdapter(adapter);
    }
    public void selectAssignment(View v){
        String mString = getResources().getString(R.string.assn_selected);

        Spinner course = (Spinner)findViewById(R.id.assnSpinner);
        String assnID = course.getSelectedItem().toString();
        Log.d("Assignment ID", assnID);
        Toast.makeText(this, mString, Toast.LENGTH_LONG).show();
        Toast.makeText(this, assnID, Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, VideoSelectActivity.class);
        i.putExtra("Assignment", assnID);
        startActivity(i);
    }

    public void addAssignment(View v) {
        startActivity(new Intent(this, NameAssignment.class));
    }

    public void goToSettings(View v){
        Intent i = new Intent(this,SettingsActivity.class);
        startActivity(i);
    }

    private void showDataSpinner() {
        db.child(globalClass.getUri()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot item : snapshot.getChildren()) {
                    //Adds each assignment name to the spinner, except for the placeholder assignment.
                    //(placeholder assignment name for every course is always 7193abc.
                    if (item.hasChild("assignmentName")) {
                        arrayList.add(item.child("assignmentName").getValue(String.class));
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AssignmentTAActivity.this, android.R.layout.simple_spinner_item, arrayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.example.workshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CourseActivity extends AppCompatActivity {

    String[] MATH_COURSES = { "MATH150", "MATH154", "MATH232", "MATH251"};
    String[] CMPT_COURSES = { "CMPT120", "CMPT213", "CMPT276", "CMPT307"};
    String[] MACM_COURSES = { "MACM101", "MACM201", "MACM316", "MACM400"};
    String[] STAT_COURSES = { "STAT100", "STAT180", "STAT270", "STAT285"};

    public GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Pedro's Code: Global Class Uri
        globalClass = (GlobalClass)getApplicationContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Intent i = getIntent();
        String subject = i.getStringExtra("Subject");
        ((TextView)findViewById(R.id.subject)).setText(subject);
        Spinner spin = (Spinner) findViewById(R.id.courseSpinner);
        String[] courses = new String[4];

        switch(globalClass.getUri()) {
            case("/math"):
                courses = MATH_COURSES;
                break;
            case("/macm"):
                courses = MACM_COURSES;
                break;
            case("/cmpt"):
                courses = CMPT_COURSES;
                break;
            case("/stat"):
                courses = STAT_COURSES;
                break;
            default:
                courses = CMPT_COURSES;
                break;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    public void selectCourse(View v){
        String mString = getResources().getString(R.string.selected_subj);
        Spinner course = (Spinner)findViewById(R.id.courseSpinner);
        String courseID = course.getSelectedItem().toString();
        Log.d("CourseID", courseID);
        Toast.makeText(this, mString, Toast.LENGTH_LONG).show();
        Toast.makeText(this, courseID, Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, AssignmentTAActivity.class);
        i.putExtra("Course", courseID);
        addDirectoryToUri(courseID);
        startActivity(i);
    }
    public void goToSettings(View v){
        Intent i = new Intent(this,SettingsActivity.class);
        startActivity(i);
    }

    public void goToLive(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void addDirectoryToUri(String course) {
        //globalClass.concatenateToUri("/" + course.toLowerCase());
        switch (globalClass.getUri()) {
            case ("/math"):
                globalClass.setUri("/math" + "/" + course.toLowerCase());
                break;
            case ("/stat"):
                globalClass.setUri("/stat" + "/" + course.toLowerCase());
                break;
            case ("/cmpt"):
                globalClass.setUri("/cmpt" + "/" + course.toLowerCase());
                break;
            case ("/macm"):
                globalClass.setUri("/macm" + "/" + course.toLowerCase());
                break;
        }
        return;
    }
}
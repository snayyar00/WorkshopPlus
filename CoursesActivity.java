package com.example;
/**
 * Courses page for Workshop+
 * Displays courses based on subject selected in previous activity
 * User can tap course buttons to proceed to the assignments page.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CoursesActivity extends AppCompatActivity {
    // Placeholder for courses from cloud database
    String[] MATH_COURSES = { "MATH150", "MATH154", "MATH232", "MATH251"};
    String[] CMPT_COURSES = { "CMPT120", "CMPT213", "CMPT276", "CMPT307"};
    String[] MACM_COURSES = { "MACM101", "MACM201", "MACM316"};
    String[] STAT_COURSES = { "STAT100", "STAT180", "STAT270", "STAT285"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Intent i = getIntent();
        String subject = i.getStringExtra("Subject");
        ((TextView)findViewById(R.id.subject)).setText(subject);
        //generateButtons(subject);
    }
    /*public void generateButtons(String subject){
        if (subject == "MATH"){
            Button[] courseButtons = new Button[MATH_COURSES.length];
            for (int i = 0; i < MATH_COURSES.length; i++){
                courseButtons[i].setText(MATH_COURSES[i]);
            }
        }
        else if (subject == "CMPT"){
            Button[] courseButtons = new Button[CMPT_COURSES.length];
            for (int i = 0; i < CMPT_COURSES.length; i++){
                courseButtons[i].setText(CMPT_COURSES[i]);
            }
        }
        else if (subject == "MACM"){
            Button[] courseButtons = new Button[MACM_COURSES.length];
            for (int i = 0; i < MACM_COURSES.length; i++){
                courseButtons[i].setText(MACM_COURSES[i]);
            }
        }
        else if (subject == "STAT"){
            Button[] courseButtons = new Button[STAT_COURSES.length];
            for (int i = 0; i < STAT_COURSES.length; i++){
                courseButtons[i].setText(STAT_COURSES[i]);
            }
        }
    }*/
}

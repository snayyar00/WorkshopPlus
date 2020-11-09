package com.example.workshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class SubjectsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        Toast.makeText(SubjectsActivity.this, "this is Subject Activity", Toast.LENGTH_SHORT).show();
    }
}
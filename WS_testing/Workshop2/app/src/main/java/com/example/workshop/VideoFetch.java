/*
DEPENDENCIES:
    implementation "androidx.recyclerview:recyclerview:1.1.0"
    implementation "androidx.recyclerview:recyclerview-selection:1.1.0-rc03"
    implementation 'com.google.android.exoplayer:exoplayer:2.10.8'
    implementation 'com.google.android.exoplayer:exoplayer-core:2.10.8'
    implementation 'com.google.android.exoplayer:exoplayer-dash:2.10.8'
    implementation 'com.google.android.exoplayer:exoplayer-hls:2.10.8'
    implementation 'com.google.android.exoplayer:exoplayer-smoothstreaming:2.10.8'
    implementation 'com.google.android.exoplayer:exoplayer-ui:2.10.8'
    implementation "androidx.cardview:cardview:1.0.0"

WHAT TO ADD TO AndroidManifest.xml:
Under <activity android:name=...>, add:
    android:configChanges="keyboardHidden|orientation|screenSize"

ON res/drawable , ADD THE FILES ic_fullscreen_expand.png AND ic_fullscreen_shrink.png !!!!!
 */


package com.example.workshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VideoFetch extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_fetch);

        mRecyclerView = findViewById(R.id.recyclerview_video);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();

        //EDIT THIS DATABASE REFERENCE AFTER TO HAVE A PROPER PATH TO THE VIDEOS. THIS IS HARD CODED.
        databaseReference = database.getReference("subjects/math100/assignment1");

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Member,ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Member, ViewHolder>(
                Member.class,
                R.layout.activity_row,
                ViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Member member, int i) {
                viewHolder.SetVideo(getApplication(), member.getVideoName(), member.getVideoUri());
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}

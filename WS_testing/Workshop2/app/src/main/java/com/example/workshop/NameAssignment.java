package com.example.workshop;

//This is the class responsible for the pop-up which appears when you click
//"select assignment" in AssignmentTAActivity.java.

/*
    CHANGES THAT MUST BE MADE:

    Add this to themes.xml:

    <style name="AppTheme.CustomTheme" >
        <item name="android:windowIsTranslucent">true</item>
        <item name="android:windowCloseOnTouchOutside">true</item>
    </style>

    Add this to androidmanifest.xml:

    <activity android:name=".NameAssignment"
            android:theme="@style/AppTheme.CustomTheme"/>

 */

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NameAssignment extends Activity {

    private DatabaseReference databaseRef;
    private EditText assignmentName;
    private GlobalClass globalClass;
    private AssignmentMember assignment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_assignment);

        globalClass = (GlobalClass)getApplicationContext();
        Button createBtn = findViewById(R.id.createAssignmentBtn);
        assignmentName = findViewById(R.id.AssignName);
        assignment = new AssignmentMember();

        //databaseRef = FirebaseStorage.getInstance().getReference(globalClass.getUri());
        databaseRef = FirebaseDatabase.getInstance().getReference("cmpt/cmpt295");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 1), (int)(height * 1));

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAssignment(assignmentName.getText().toString());
            }
        });

    }

    public void AddAssignment(String assignmentNameOriginal) {
        String assignmentNameLowerCase = assignmentNameOriginal.toLowerCase();
        assignmentNameLowerCase = assignmentNameLowerCase.replaceAll(" ", "_");
        Log.i("assignment var name", assignment.getAssignmentName());

        assignment.setAssignmentName(assignmentNameOriginal);

        AssignmentMember assignment = new AssignmentMember(assignmentNameOriginal);

        //Add assignment to specified path in firebase.
        databaseRef = FirebaseDatabase.getInstance().getReference(globalClass.getUri());
        databaseRef = databaseRef.child(assignmentNameLowerCase);
        databaseRef.setValue(assignment);

        String mString = getResources().getString(R.string.assn_created);
        Toast.makeText(this, mString, Toast.LENGTH_SHORT).show();
        finish();

        return;
    }
}

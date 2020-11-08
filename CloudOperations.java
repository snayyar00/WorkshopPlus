package com.example.getalldata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.os.Bundle;
import android.view.View;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CloudOperations extends AppCompatActivity {

    private Button uploadBtn;
    private Button cancelBtn;
    private VideoView videoView;
    private EditText videoName;
    private Uri videoUri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set videoUri manually to test
        videoUri = null;

        uploadBtn = findViewById(R.id.upload_button);
        cancelBtn = findViewById(R.id.cancel_button);
        videoView = findViewById(R.id.videoView);
        videoName = findViewById(R.id.video_name);

        //TEMPORARY: Getting a sample video to play.
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.samplevideo;
        videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);
        videoView.start();

        //FOR databaseReference: Change path depending on which workshop, course, and assignment is present.
        databaseReference = FirebaseDatabase.getInstance().getReference("subjects/math100/assignment1");
        storageReference = FirebaseStorage.getInstance().getReference("videos");

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadVideo();
            }
        });

    }

    private String getFileExtension(Uri videoUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));
    }

    private void uploadVideo() {
        if (videoUri != null) {
            StorageReference reference = storageReference.child(videoName + "." + getFileExtension(videoUri));

            reference.putFile(videoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Upload Successful", Toast.LENGTH_SHORT).show();
                            Member member = new Member(videoName.getText().toString().trim(), taskSnapshot.getUploadSessionUri().toString());
                            String upload = databaseReference.push().getKey();
                            databaseReference.child(upload).setValue(member);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }
}

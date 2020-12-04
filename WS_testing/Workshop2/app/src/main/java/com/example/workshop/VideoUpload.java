package com.example.workshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.workshop.Member;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class VideoUpload extends AppCompatActivity {

    VideoView videoView;
    Button uploadBtn;
    Button chooseVideoBtn;
    EditText videoNameInput;
    private Uri videoUri;
    MediaController mediaController;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Member member;
    UploadTask uploadTask;

    private static final int PICK_VIDEO_REQUEST = 1;
    GlobalClass globalClass;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_upload);

        globalClass = (GlobalClass)getApplicationContext();

        videoView = findViewById(R.id.videoView);
        videoNameInput = findViewById(R.id.video_name);
        uploadBtn = findViewById(R.id.upload_button);
        chooseVideoBtn = findViewById(R.id.choose_video_button);

        member = new Member();
        storageReference = FirebaseStorage.getInstance().getReference("videos");
        databaseReference = FirebaseDatabase.getInstance().getReference(globalClass.getUri());


        //TEMPORARY: Getting a sample video to play.
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.samplevideo;
        videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);
        videoView.start();

        chooseVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseVideo();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadVideo();
            }
        });

    }

    public void ChooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
        }
    }

    private String getExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void UploadVideo() {
        String videoName = videoNameInput.getText().toString();
        if ((videoUri != null) || (!TextUtils.isEmpty(videoName))) {

            final StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getExt(videoUri));
            uploadTask = reference.putFile(videoUri);


            //ACTUALLY UPLOADING TO FIREBASE + GETTING THE VIDEO URI FROM STORAGE AND PUTTING IT IN THE DATABASE
            Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        String mString = getResources().getString(R.string.upload_success);
                        Uri downloadUrl = task.getResult();
                        Toast.makeText(VideoUpload.this, mString, Toast.LENGTH_SHORT).show();

                        //MAY NOT BE CORRECT: WATCH VIDEO AGAIN.
                        member.setVideoName(videoNameInput.getText().toString());
                        member.setVideoUri(downloadUrl.toString());
                        String i = databaseReference.push().getKey();
                        databaseReference.child(i).setValue(member);
                    } else {
                        String mString = getResources().getString(R.string.upload_fail);
                        Toast.makeText(VideoUpload.this, mString, Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } else {
            String mString = getResources().getString(R.string.emptyField_err);
            Toast.makeText(VideoUpload.this, mString, Toast.LENGTH_SHORT).show();
        }
    }

/*
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
        setContentView(R.layout.cloudops);

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
                            //BELOW: What sets the video URI in the database. It's getting the wrong uri.
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

 */
}

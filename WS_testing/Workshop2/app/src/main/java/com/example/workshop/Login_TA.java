package com.example.workshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.workshop.R;


public class Login_TA extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn,forgotTextLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__t);

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginBtn);
        mCreateBtn = findViewById(R.id.createText);
        forgotTextLink = findViewById(R.id.forgotPassword);
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //localize for language
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name); // 1

        TextView textView = findViewById(R.id.Welcome);
        textView.setText(R.string.my_string_name); // 2


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    String mString = getResources().getString(R.string.email_req);
                    mEmail.setError(mString);
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    String mString = getResources().getString(R.string.pass_req);
                    mPassword.setError(mString);
                    return;
                }

                if(password.length() < 6){
                    String mString = getResources().getString(R.string.pass_short);
                    mPassword.setError(mString);
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);



                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override


                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser fUser = fAuth.getCurrentUser();
                            userID = fAuth.getCurrentUser().getUid();


                            if(userID != null){
                                DocumentReference docref = db.collection("users").document(userID);
                                docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                String student = document.getString("Student?");
                                                int valueForTA = Integer.valueOf(student);
                                                String mString = getResources().getString(R.string.login_succ);
                                                Toast.makeText(Login_TA.this, mString, Toast.LENGTH_SHORT).show();
                                                // valueForTA 0 is math TA
                                                if(valueForTA == 0) {
                                                    String mString2 = getResources().getString(R.string.hello_ta);
                                                    Toast.makeText(Login_TA.this, mString2, Toast.LENGTH_SHORT).show();
                                                    //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                    startActivity(new Intent(getApplicationContext(),SubjectsActivity.class));
                                                }
                                                // valueForTA 2 is stat TA
                                                else if(valueForTA==2) {
                                                    String mString2 = getResources().getString(R.string.hello_ta);
                                                    Toast.makeText(Login_TA.this, mString2, Toast.LENGTH_SHORT).show();
                                                    //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                    startActivity(new Intent(getApplicationContext(),SubjectsActivity.class));
                                                } else {
                                                    String mString3 = getResources().getString(R.string.hello_student);
                                                    Toast.makeText(Login_TA.this, mString3, Toast.LENGTH_SHORT).show();
                                                    //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                    startActivity(new Intent(getApplicationContext(),SubjectsActivity.class));
                                                }

                                            }
                                        }
                                    }
                                });

                            }


                        }else {
                            String mString = getResources().getString(R.string.show_error);
                            Toast.makeText(Login_TA.this, mString + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register_TA.class));
            }
        });

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mString = getResources().getString(R.string.reset_pass);
                String mString2 = getResources().getString(R.string.reset_enterEmail);
                String mString3 = getResources().getString(R.string.show_yes);
                String mString4 = getResources().getString(R.string.reset_sent);
                String mString5 = getResources().getString(R.string.reset_error);

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());

                passwordResetDialog.setTitle(mString);
                passwordResetDialog.setMessage(mString2);
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton(mString3, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login_TA.this, mString4, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login_TA.this, mString5 + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                String my_String = getResources().getString(R.string.show_no);
                passwordResetDialog.setNegativeButton(my_String, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });


    }
}
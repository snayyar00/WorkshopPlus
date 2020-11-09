package com.example.workshop;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
Button rety;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        rety =(Button)findViewById(R.id.rety);

        rety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SplashScreen.this, SplashScreen.class);
                startActivity(i);
                finish();
            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            new Handler().postDelayed(new Runnable() {




                @Override

                public void run() {

                    Intent i = new Intent(SplashScreen.this, Login_TA.class);

                    startActivity(i);



                    finish();

                }

            }, 2000);
        }

        else{
            Toast.makeText(SplashScreen.this, "NO connection ", Toast.LENGTH_SHORT).show();



        }

    }
    
}
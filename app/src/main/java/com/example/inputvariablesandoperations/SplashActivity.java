package com.example.inputvariablesandoperations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private TextView version,releasedate; private String versionnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setStatusBarColor(ContextCompat.getColor(SplashActivity.this,R.color.black));
        version = findViewById(R.id.version_text);
        releasedate = findViewById(R.id.releasedate_text);

        versionnumber = BuildConfig.VERSION_NAME;

        version.setText("Version" + versionnumber);
        releasedate.setText("Release date 28/3/2023");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish(); // explore the class Intent.
            }
        }, 3350); // Number is in milliseconds.
    }
}

package com.pingoo.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import static com.pingoo.mynotes.RegistrationActivity.NAME;
import static com.pingoo.mynotes.RegistrationActivity.SHARED_PREFS;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        final String name = sharedPreferences.getString(NAME, "");

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(name.isEmpty()) {
                    Intent intent = new Intent(SplashActivity.this, RegistrationActivity.class);
                    SplashActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(intent);
                }

                SplashActivity.this.finish();
            }
        }, 2000);
    }
}

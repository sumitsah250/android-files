package com.example.sharedpreference_loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
                Boolean check= pref.getBoolean("flag",false);
                pref.getString("id","1234");
                pref.getString("password","1234");
                Intent iNext;
                if(check){ //for true (user is logged in)
                    iNext = new Intent(MainActivity.this,homescreen.class);
                }else{ // first time login or user is logout
                    iNext = new Intent(MainActivity.this,loginscreen.class);
                }
                startActivity(iNext);

            }
        },3000);
    }
}
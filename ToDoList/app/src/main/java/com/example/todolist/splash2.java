package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class splash2 extends AppCompatActivity {
    TextView txt1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txt1=findViewById(R.id.txt12);
        setContentView(R.layout.activity_splash2);
        Intent home = new Intent(splash2.this,MainActivity.class);
        //Animation alpha1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
//        txt1.setAnimation(alpha1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(home);
                finish();
            }
        },3000);
    }
}
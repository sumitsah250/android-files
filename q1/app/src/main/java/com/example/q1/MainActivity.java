package com.example.q1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1;
        btn1 = findViewById(R.id.btn1);

        Intent iNext;
        iNext= new Intent(MainActivity.this,secondActivity.class);
        iNext.putExtra("title","HOME");
        iNext.putExtra("StudentName","sumit");
        iNext.putExtra("ROll NO",1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(iNext);
            }
        });

    }
}
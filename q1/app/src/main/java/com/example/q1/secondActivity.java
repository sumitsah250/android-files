package com.example.q1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class secondActivity extends AppCompatActivity {

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView name2;
        name2=findViewById(R.id.name2);

        Intent fromAct = getIntent();
        String title=fromAct.getStringExtra("title");
        String name=fromAct.getStringExtra("StudentName");
        int roll=fromAct.getIntExtra("ROll NO",0);
        name2.setText("Roll no:"+roll+" name:"+name);
//        Objects.requireNonNull(getSupportActionBar()).setTitle(title);

    }
}
package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Chronometer chronometer;
    private boolean running;
    ListView listview;
    TextView txtVIew;

     int offset;
     ArrayList<String> time = new ArrayList<>();
     Button btn4;
    ArrayAdapter<String> adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer=findViewById(R.id.chronometer);
        listview=findViewById(R.id.listview);
        btn4=findViewById(R.id.btn4);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,time);
        listview.setAdapter(adapter);
        txtVIew= findViewById(R.id.txt);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.add(chronometer.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }
   public void startchronometer(View v){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime()-offset);
            chronometer.start();
            running = true;
        }
       }
    public void resetchronometer(View v){
        chronometer.setBase(SystemClock.elapsedRealtime());
        offset=0;
        finish();
        startActivity(getIntent());

    }
       public void stopchronometer(View view){
        if(running){
            chronometer.stop();
            offset = (int)(SystemClock.elapsedRealtime()-chronometer.getBase());
            running=false;
        }
       }

}
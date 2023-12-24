package com.example.q3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Intent i1,i2;

    ListView List1;
    ArrayList<String> arrnames = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List1=findViewById(R.id.List1);
        i1= new Intent(MainActivity.this,spinner.class);
        i2= new Intent(MainActivity.this,auto.class);
        arrnames.add("spinner view");
        arrnames.add("auto view");
        arrnames.add("aaditya");
        arrnames.add("bikash");
        arrnames.add("subesh");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,arrnames);
        List1.setAdapter(adapter);
        List1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    startActivity(i1);}
                    else if(position==1){
                    startActivity(i2);
                 } else {
                    Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
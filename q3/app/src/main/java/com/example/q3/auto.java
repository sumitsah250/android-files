package com.example.q3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

public class auto extends AppCompatActivity {
    AutoCompleteTextView auto1;
    ArrayList<String> arr2 = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        auto1=findViewById(R.id.auto1);
        arr2.add("nepal");
        arr2.add("india");
        arr2.add("china");
        arr2.add("america");
        arr2.add("abudabi");
        ArrayAdapter<String> adapter1= new ArrayAdapter<>(auto.this,android.R.layout.simple_spinner_dropdown_item,arr2);
        auto1.setAdapter(adapter1);
    }
}
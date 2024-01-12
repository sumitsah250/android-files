package com.example.database1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydbhelper1 dbHelper = new mydbhelper1(getApplicationContext());
        dbHelper.addContacts("sumit","98095");
        dbHelper.addContacts("aaditya","3560");
        dbHelper.addContacts("bidur","98041225");
        dbHelper.addContacts("bdur","980961259");

    }
}
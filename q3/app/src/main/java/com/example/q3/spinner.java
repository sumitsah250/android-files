package com.example.q3;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class spinner extends AppCompatActivity {
    Spinner s1;
    ArrayList<String> arrid = new ArrayList<>();
    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        s1=findViewById(R.id.s1);
        arrid.add("national id card");
        arrid.add("nagarikta");
        arrid.add("passport");
        arrid.add("lisense");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(spinner.this,android.R.layout.simple_spinner_dropdown_item ,arrid );
        s1.setAdapter(adapter1);
    }
}
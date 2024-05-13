package com.example.all_about_recycler;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.all_about_recycler.adapter.test;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<fruitdetails> arrayList = new ArrayList<>();
        RecyclerView recyclerView= findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList.add(new fruitdetails(R.drawable.ic_launcher_foreground,"Apple","this is good for health"));
        arrayList.add(new fruitdetails(R.drawable.ic_launcher_foreground,"Banana","this is good for health"));
        arrayList.add(new fruitdetails(R.drawable.baseline_airline_seat_individual_suite_24,"Apple","this is good for health"));
        arrayList.add(new fruitdetails(R.drawable.baseline_airline_seat_individual_suite_24,"Apple","this is good for health"));
        arrayList.add(new fruitdetails(R.drawable.ic_launcher_background,"Apple","this is good for health"));

        test adapter = new test(this,arrayList);
        recyclerView.setAdapter(adapter);


    }
}
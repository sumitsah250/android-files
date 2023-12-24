package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    Spinner spinner;
    AutoCompleteTextView actxtView;
    int[] arrNo =new int[]{1,2,3,4,5};
    ArrayList<String> arrNames=new ArrayList<>();
    ArrayList<String>  arrIds = new ArrayList<>();
    ArrayList<String> arrLanguages = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actxtView=findViewById(R.id.actxtView);
        spinner=findViewById(R.id.spinner);
        listview=findViewById(R.id.listview);
        arrNames.add("sumit");
        arrNames.add("aaditya");
        arrNames.add("aashis");
        arrNames.add("sumit");
        arrNames.add("aaditya");
        arrNames.add("aashis");
        arrNames.add("sumit");
        arrNames.add("aaditya");
        arrNames.add("aashis");
        arrNames.add("sumit");
        arrNames.add("aaditya");
        arrNames.add("aashis");
        arrNames.add("sumit");
        arrNames.add("aaditya");
        arrNames.add("aashis");
        arrNames.add("sumit");
        arrNames.add("aaditya");
        arrNames.add("aashis");
        arrNames.add("sumit");
        arrNames.add("aaditya");
        arrNames.add("aashis");
        arrNames.add("sumit");
        arrNames.add("aaditya");
        arrNames.add("aashis");arrNames.add("aaditya");
        arrNames.add("aashis");
        arrNames.add("sumit");
        arrNames.add("aaditya");
        arrNames.add("aashis");arrNames.add("aaditya");
        arrNames.add("aashis");
        arrNames.add("sumit");
        arrNames.add("aaditya");
        arrNames.add("aashis");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,arrNames);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0 ){
                    Toast.makeText(MainActivity.this, "first file", Toast.LENGTH_SHORT).show();
                }else if(position ==1){
                    Toast.makeText(MainActivity.this, "second", Toast.LENGTH_SHORT).show();
                }
            }
        });
        arrIds.add("driving lisense");
        arrIds.add("national id");
        arrIds.add("passport");
        arrIds.add("Pan card");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,arrIds);
        spinner.setAdapter(spinnerAdapter);
        arrLanguages.add("nepali");
        arrLanguages.add("hindi");
        arrLanguages.add("english");
        ArrayAdapter<String> autvAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,arrLanguages);
        actxtView.setAdapter(autvAdapter);
        actxtView.setThreshold(1);

    }
}
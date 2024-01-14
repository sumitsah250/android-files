package com.example.database1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Contactmodel contactmodel = new Contactmodel();
        contactmodel.name="000000";
        contactmodel.id=2;
        contactmodel.number="0000";
        mydbhelper1 dbHelper;
        ListView listview;
        listview= findViewById(R.id.listview);
        dbHelper= new mydbhelper1(this);
        String s = "sumit";
        String p ="aaditya";
        dbHelper.addContacts(s,p);
        dbHelper.addContacts("aaditya","3560");
        dbHelper.addContacts("bidur","98041225");
        dbHelper.addContacts("bdur","980961259");
        dbHelper.UpdateContect(contactmodel);
        dbHelper.DeleteContact(contactmodel);

         ArrayList<Contactmodel> arrcontacts = dbHelper.getcontect();
         ArrayList<String> arrnames = new ArrayList<>();
         for(int i=0;i<arrcontacts.size();i++){
             arrnames.add(arrcontacts.get(i).name);
         }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrnames);
         listview.setAdapter(adapter);
        Toast.makeText(this, arrcontacts.get(5).name, Toast.LENGTH_SHORT).show();


    }

}
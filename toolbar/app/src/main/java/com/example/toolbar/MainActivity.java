package com.example.toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        //step 1
        setSupportActionBar(toolbar);
        //step 2
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("TOOLBAR");
        }
        toolbar.setTitle("TOOLBAR");
        toolbar.setSubtitle("subtitle");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.opt_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.opt_new){
            Toast.makeText(this, "create a new folder", Toast.LENGTH_SHORT).show();
        } else if(itemId==R.id.opt_save){
            Toast.makeText(this, "folder saved ", Toast.LENGTH_SHORT).show();
        }else if(itemId == R.id.opt_open){
            Toast.makeText(this, "new file opened", Toast.LENGTH_SHORT).show();
        }else if(itemId==android.R.id.home){
            Toast.makeText(this, "go back", Toast.LENGTH_SHORT).show();
            super.getOnBackPressedDispatcher();
        }
        return super.onOptionsItemSelected(item);
    }
}
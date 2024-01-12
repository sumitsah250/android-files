package com.example.todolist;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    FloatingActionButton floating;
    ArrayList<taskModel> task = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floating = findViewById(R.id.floating);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        task.add(new taskModel("my first task","2080/09/12","2:00"));
        task.add(new taskModel("my second  task","2080/10/12","2:00"));
        task.add(new taskModel("home work","2080/09/10","2:00"));
        task.add(new taskModel("class","2080/1/12","3:00"));
        task.add(new taskModel("study","2080/1/12","3:00"));
        RecyclerAdapter1 adapter = new RecyclerAdapter1(MainActivity.this,task);
        recyclerView.setAdapter(adapter);
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_task);
                EditText edttask = dialog.findViewById(R.id.edttask);
                EditText edtdate = dialog.findViewById(R.id.edtdate);
                EditText edttime = dialog.findViewById(R.id.edttime);
                Button btnAction = dialog.findViewById(R.id.btnAction);
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String task1 ="";
                        String date1= "";
                        String time1="";
                        if(!edttask.getText().toString().equals("")){
                            task1=edttask.getText().toString();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "task can't be empty", Toast.LENGTH_SHORT).show();
                        }
                        if(!edtdate.getText().toString().equals("")){
                            date1=edtdate.getText().toString();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "date missing", Toast.LENGTH_SHORT).show();
                        }
                        if(!edttime.getText().toString().equals("")){
                            time1=edttime.getText().toString();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "time missing", Toast.LENGTH_SHORT).show();
                        }
                        if(!edttask.getText().toString().equals("") && !edttime.getText().toString().equals("") && !edtdate.getText().toString().equals("")){
                            task.add(new taskModel(task1,date1,time1));
                            adapter.notifyItemInserted(task.size()-1);
                            recyclerView.scrollToPosition(task.size()-1);
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
}
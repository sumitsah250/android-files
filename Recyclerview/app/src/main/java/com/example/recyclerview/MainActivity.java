package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ContactModel> arrcontacts = new ArrayList<>();
    FloatingActionButton btnOpenDialog;
    RecyclerContactAdapter adapter;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView =findViewById(R.id.recyclerContact);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnOpenDialog=findViewById(R.id.btnOpenDialog);
        arrcontacts.add(new ContactModel(R.drawable.aashis,"Aashis","9816879933"));
        arrcontacts.add(new ContactModel(R.drawable.b,"Sumit","980986123"));
        arrcontacts.add(new ContactModel(R.drawable.c,"Bidur","98098612"));
        arrcontacts.add(new ContactModel(R.drawable.d,"D","9809861"));
        arrcontacts.add(new ContactModel(R.drawable.a,"E","9809861235"));
        arrcontacts.add(new ContactModel(R.drawable.b,"F","980986123"));
        arrcontacts.add(new ContactModel(R.drawable.d,"G","98098612"));
        arrcontacts.add(new ContactModel(R.drawable.c,"H","9809861"));
        arrcontacts.add(new ContactModel(R.drawable.d,"I","9809861235"));
        arrcontacts.add(new ContactModel(R.drawable.a,"J","980986123"));
        arrcontacts.add(new ContactModel(R.drawable.a,"K","98098612"));
        arrcontacts.add(new ContactModel(R.drawable.a,"L","9809861"));
        arrcontacts.add(new ContactModel(R.drawable.a,"M","9809861235"));
        arrcontacts.add(new ContactModel(R.drawable.a,"N","980986123"));
        arrcontacts.add(new ContactModel(R.drawable.a,"O","98098612"));
        arrcontacts.add(new ContactModel(R.drawable.a,"P","9809861"));
        arrcontacts.add(new ContactModel(R.drawable.b,"Sumit","980986123"));
        arrcontacts.add(new ContactModel(R.drawable.c,"Bidur","98098612"));
        arrcontacts.add(new ContactModel(R.drawable.d,"D","9809861"));
        arrcontacts.add(new ContactModel(R.drawable.a,"E","9809861235"));
        arrcontacts.add(new ContactModel(R.drawable.b,"F","980986123"));
        arrcontacts.add(new ContactModel(R.drawable.d,"G","98098612"));
        arrcontacts.add(new ContactModel(R.drawable.c,"H","9809861"));
        arrcontacts.add(new ContactModel(R.drawable.d,"I","9809861235"));
        arrcontacts.add(new ContactModel(R.drawable.a,"J","980986123"));
        arrcontacts.add(new ContactModel(R.drawable.a,"K","98098612"));
        arrcontacts.add(new ContactModel(R.drawable.a,"L","9809861"));
        arrcontacts.add(new ContactModel(R.drawable.a,"M","9809861235"));
        arrcontacts.add(new ContactModel(R.drawable.a,"N","980986123"));
        arrcontacts.add(new ContactModel(R.drawable.a,"O","98098612"));
        arrcontacts.add(new ContactModel(R.drawable.a,"P","9809861"));
        RecyclerContactAdapter adapter = new RecyclerContactAdapter(MainActivity.this,arrcontacts);
        recyclerView.setAdapter(adapter);
//        RecycleCO adapter1 =new RecycleCO(MainActivity.this, arrcontacts);
//        recyclerView.setAdapter(adapter1);
        recyclerView.setOnContextClickListener(new View.OnContextClickListener() {
            @Override
            public boolean onContextClick(View v) {
                Toast.makeText(MainActivity.this, "this was called", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add);
                EditText edtname = dialog.findViewById(R.id.edtname);
                EditText edtnumber = dialog.findViewById(R.id.edtnumber);
                Button btnAction = dialog.findViewById(R.id.btnAction);
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = "";
                        String number = "";
                        if (!edtname.getText().toString().equals("")) {
                            name = edtname.getText().toString();
                        } else
                            Toast.makeText(MainActivity.this, "please enter contact name", Toast.LENGTH_SHORT).show();
                        if (!edtnumber.getText().toString().equals("")) {
                            number = edtnumber.getText().toString();
                        } else {
                            Toast.makeText(MainActivity.this, "please enter contact number", Toast.LENGTH_SHORT).show();
                        }
                        arrcontacts.add(new ContactModel(R.drawable.a,name, number));
                        adapter.notifyItemInserted(arrcontacts.size()-1);
                        recyclerView.scrollToPosition(arrcontacts.size()-1);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }


}
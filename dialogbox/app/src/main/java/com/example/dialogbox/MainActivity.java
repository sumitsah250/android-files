package com.example.dialogbox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//        alertDialog.setTitle("terms and condition");
//        alertDialog.setIcon(R.drawable.ic_launcher_background);
//        alertDialog.setMessage("are you sure want to delete this");
//
//        alertDialog.setButton("yes i have read",new DialogInterface.OnClickListener(){
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//


        AlertDialog.Builder delDialog = new AlertDialog.Builder(MainActivity.this);
        delDialog.setTitle("delete");
        delDialog.setIcon(R.drawable.ic_launcher_background);
        delDialog.setMessage("sure want to delete it ");
        delDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "item deleted", Toast.LENGTH_SHORT).show();
            }
        });
        delDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "...", Toast.LENGTH_SHORT).show();

            }
        });
        delDialog.show();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitDialog = new AlertDialog.Builder(MainActivity.this);
        exitDialog.setTitle("Exit");
        exitDialog.setMessage("are you sure want to exit");
        exitDialog.setIcon(R.drawable.ic_launcher_background);
            exitDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.super.onBackPressed();
                }
            });
            exitDialog.setNegativeButton("no",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            exitDialog.setNeutralButton("cancel", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            exitDialog.show();
        }
    }
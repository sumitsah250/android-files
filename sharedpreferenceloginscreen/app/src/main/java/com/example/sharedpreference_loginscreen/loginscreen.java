package com.example.sharedpreference_loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginscreen extends AppCompatActivity {

    Button btnlogin,btncreate,btnlogin_create;


    EditText userid,userpassword,D_userid,D_userpassword,D_userpassword1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);
        btnlogin= findViewById(R.id.btnlogin);
        userid = findViewById(R.id.userid);
        btnlogin_create = findViewById(R.id.btnlogin_create);
        userpassword = findViewById(R.id.userpassword);
        Dialog dialog= new Dialog(loginscreen.this);
        dialog.setContentView(R.layout.adddialog);
        D_userid = dialog.findViewById(R.id.D_userid);
        D_userpassword = dialog.findViewById(R.id.D_userpassword);
        D_userpassword1 = dialog.findViewById(R.id.D_userpassword1);
        btncreate =dialog.findViewById(R.id.btncreate);
        SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Intent iHome = new Intent(loginscreen.this,homescreen.class);


//
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userid.getText().toString().equals(pref.getString("id", "1234")) && userpassword.getText().toString().equals(pref.getString("password", "1234")))
                {
                    editor.putBoolean("flag",true);
                    editor.apply();
                    startActivity(iHome);
                }else{
                    Toast.makeText(loginscreen.this, pref.getString("id","invalid id or password "), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnlogin_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(D_userpassword.getText().toString().equals(D_userpassword1.getText().toString()) && D_userpassword.getText().toString()!="" && D_userid.getText().toString()!=""){
                    editor.putString("id",D_userid.getText().toString());
                    editor.putString("password",D_userpassword.getText().toString());
                    dialog.dismiss();
                    editor.apply();
                    Toast.makeText(loginscreen.this, "new password set sucessfully", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(loginscreen.this, "unable to create password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
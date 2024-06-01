package com.example.all_about_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registeractivity extends AppCompatActivity {
    EditText edtregemail,edtregpassword;
    Button btnregister;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registeractivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.logout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtregemail=findViewById(R.id.edtregemail);
        edtregpassword=findViewById(R.id.edtregpassword);
        btnregister=findViewById(R.id.ButtonRegister);
        auth = FirebaseAuth.getInstance();


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_email=edtregemail.getText().toString();
                String text_password = edtregpassword.getText().toString();
                if(TextUtils.isEmpty(text_password) || TextUtils.isEmpty(text_email)){
                    Toast.makeText(registeractivity.this, "invalid credentials", Toast.LENGTH_SHORT).show();
                }else if(text_password.length()<6){
                    Toast.makeText(registeractivity.this, "password length must be greater than 6", Toast.LENGTH_SHORT).show();
                }else {
                    retgisteruser(text_email,text_password);
                }
            }
        });
    }

    private void retgisteruser(String textEmail, String textPassword) {
        auth.createUserWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(registeractivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(registeractivity.this, "user register successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(registeractivity.this,tinku.class));
                    finish();
                }else{
                    Toast.makeText(registeractivity.this, "failed to register the user", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
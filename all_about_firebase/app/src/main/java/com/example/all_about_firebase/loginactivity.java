package com.example.all_about_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginactivity extends AppCompatActivity {
    EditText edtlogemail,edtlogpsword;
    Button btnlogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.logout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtlogemail=findViewById(R.id.edtlogemail);
        edtlogpsword=findViewById(R.id.edtlogpassword);
        btnlogin =findViewById(R.id.ButtonLogin);
        auth=FirebaseAuth.getInstance();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_email = edtlogemail.getText().toString();
                String text_password = edtlogpsword.getText().toString();
                 auth.signInWithEmailAndPassword(text_email,text_password).addOnSuccessListener(loginactivity.this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(loginactivity.this, "login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(loginactivity.this, tinku.class));
                         finish();                    }
                });

            }
        });


    }

    private void loginUser(String textEmail, String textPassword) {

    }
}
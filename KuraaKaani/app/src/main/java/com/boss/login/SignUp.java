package com.boss.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.boss.kuraakaani.MainActivity;
import com.boss.kuraakaani.R;
import com.boss.kuraakaani.databinding.ActivitySignUpBinding;
import com.boss.modelclass.myUsers;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email="";
                String password="";

                if(binding.email.getText().toString()!=""){
                    email=binding.email.getText().toString();
                }else{
                    binding.email.setError("email can't be empty");
                }
                if(binding.password.getText().toString().equals(binding.confirmPassword.getText().toString())){
                    if(binding.password.getText().toString().length()>=6){
                        password=binding.password.getText().toString();
                    }else{
                        binding.password.setError("Password can't be less than 6 words");
                    }
                }else{
                    binding.password.setError("Password and confirm Password dose not  match");
                    binding.confirmPassword.setError("Password and confirm Password dose not  match");
                     }

                if( !email.equals("") && !password.equals("")){
                    signupuser(binding.email.getText().toString(),binding.password.getText().toString());
                }

            }
        });
        binding.loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,Login.class));
                finish();
            }
        });

    }
    public void signupuser(String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent home = new Intent(SignUp.this, setupActivity.class);
                    home.putExtra("password",password);
                    startActivity(home);
                    finish();
                }else{
                    Toast.makeText(SignUp.this, "invalid email", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
package com.boss.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.boss.kuraakaani.MainActivity;
import com.boss.kuraakaani.R;
import com.boss.kuraakaani.databinding.ActivityMainBinding;
import com.boss.kuraakaani.databinding.ActivitySetupBinding;
import com.boss.modelclass.myUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class setupActivity extends AppCompatActivity {
    ActivitySetupBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    Uri selectedimage;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding= ActivitySetupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        dialog= new ProgressDialog(this);
        dialog.setMessage("Updating Profile >>>");
        dialog.setCancelable(false);

        binding.profilepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });
        binding.btnsetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.edtusername.getText().toString();
                if(!name.equals("")){
                    dialog.show();
                    if(selectedimage != null){
                        StorageReference reference = firebaseStorage.getReference().child("Profiles").child(firebaseAuth.getUid());
                        reference.putFile(selectedimage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if(task.isSuccessful()){
                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageUrl = uri.toString();
                                            String uid = firebaseAuth.getUid();
                                            String email= firebaseAuth.getCurrentUser().getEmail();
                                            Intent intent = new Intent();
                                            String password = intent.getStringExtra("password");
                                            myUsers myUsers=new myUsers(uid,imageUrl,email,name,password);
                                            firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).setValue(myUsers).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    dialog.dismiss();
                                                    Intent intent = new Intent(setupActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });
                                        }
                                    });
                                }

                            }
                        });
                    }else{
                        String uid = firebaseAuth.getUid();
                        String email= firebaseAuth.getCurrentUser().getEmail();
                        Intent intent = new Intent();
                        String password = intent.getStringExtra("password");
                        myUsers myUsers=new myUsers(uid,"No Image",email,name,password);
                        firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).setValue(myUsers).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dialog.dismiss();
                                Intent intent = new Intent(setupActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }else{
                    binding.edtusername.setError("username can't be empty");
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(data.getData()!=null){
                binding.profilepicture.setImageURI(data.getData());
                selectedimage=data.getData();
            }
        }
    }
}
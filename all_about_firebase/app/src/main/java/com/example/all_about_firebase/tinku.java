package com.example.all_about_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;

public class tinku extends AppCompatActivity {
    Button logout;
    private ListView listView;
    myUser myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tinku);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        listView= findViewById(R.id.listview);

        FirebaseDatabase.getInstance().getReference().child("programming knowledge").child("android").setValue("abcd");
        FirebaseDatabase.getInstance().getReference().child("sumit").child("aadtya").setValue("sumit");
        HashMap<String,Object> map = new HashMap<>();
        map.put("Name","sumit");
        map.put("email","sumit@gmail.com");

        FirebaseDatabase.getInstance().getReference().child("programming knowledge").updateChildren(map);
        FirebaseDatabase.getInstance().getReference().child("programming knowledge").child("multiple values").updateChildren(map);
        FirebaseDatabase.getInstance().getReference().child("programming knowledge").child("multiple values").push().updateChildren(map); //push will create a unique id

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    myUser= snapshot1.getValue(myUser.class);
                    String info = myUser.getEmail()+" : "+ myUser.getPassword();
                    list.add(info);
//                    list.add(snapshot1.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        logout=findViewById(R.id.Logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(tinku.this, "logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(tinku.this,StartActivity.class));
                finish();
            }
        });
    }
}
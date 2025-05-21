package com.boss.pustakbazzar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellerDetails extends AppCompatActivity {

    private AppCompatButton btnName,btnContact;
    private AppCompatButton  btnLocation,btnEmail;
    private FloatingActionButton btnback;
    private CircleImageView profilePic;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private FusedLocationProviderClient fusedLocationClient;


    private Uri imageUri;
    private double updatedLatitude = 0.0, updatedLongitude = 0.0;
    private String userId;
    ProgressDialog progressDialog;


    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    profilePic.setImageURI(imageUri);
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seller_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnName = findViewById(R.id.btnName);
        btnLocation = findViewById(R.id.btnlocation);
        btnback=findViewById(R.id.btnback);
        btnContact=findViewById(R.id.btncontact);
        btnEmail=findViewById(R.id.btnEmail);
        progressDialog = new ProgressDialog(this);

        profilePic = findViewById(R.id.edit_profile_pic);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        Intent intent = getIntent();
        userId = intent.getStringExtra("userid");


        loadUserProfile();



    }

    private void loadUserProfile() {
        // Initialize and show progress dialog
        progressDialog.setMessage("Loading profile...");
        progressDialog.setCancelable(true); // Let user cancel it
        progressDialog.setOnCancelListener(dialog -> {
            Toast.makeText(this, "Cancelled. Closing profile...", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if user cancels
        });
        progressDialog.show();

        // Fetch Firestore data
        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (progressDialog.isShowing()) progressDialog.dismiss();

                    if (documentSnapshot.exists()) {
                        btnName.setText(documentSnapshot.getString("name"));
                        btnContact.setText(documentSnapshot.getString("ContactNumber"));
                        btnEmail.setText(documentSnapshot.getString("email"));
                        updatedLatitude = documentSnapshot.getDouble("latitude");
                        updatedLongitude = documentSnapshot.getDouble("longitude");

                        btnLocation.setText("Lat: " + updatedLatitude + ", Lon: " + updatedLongitude);

                        String imageUrl = documentSnapshot.getString("profileImage");
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Glide.with(this).load(imageUrl).into(profilePic);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    if (progressDialog.isShowing()) progressDialog.dismiss();
                    Toast.makeText(this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                });
    }







}
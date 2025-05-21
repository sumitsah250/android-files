package com.boss.pustakbazzar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import android.Manifest;

public class UserProfile extends AppCompatActivity {

    private EditText etName,edtContact;
    private AppCompatButton btnSave;
    private EditText btnLocation;
    private FloatingActionButton  btnback;
    private CircleImageView profilePic;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private FusedLocationProviderClient fusedLocationClient;


    private Uri imageUri;
    private double updatedLatitude = 0.0, updatedLongitude = 0.0;
    private String userId;
    private ProgressDialog progressDialog;
    private String newContact="98xxxxxxxx";


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
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.etName);
        btnSave = findViewById(R.id.btnsave);
        btnLocation = findViewById(R.id.btnlocation);
        btnback=findViewById(R.id.btnback);
        edtContact=findViewById(R.id.edtcontact);

        profilePic = findViewById(R.id.edit_profile_pic);
        ImageView changeImage = findViewById(R.id.edt_change_image);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //progress dialog initialization
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating profile...");
        progressDialog.setCancelable(false); // Prevents dismissal on back press
        //progress dialog initialization


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        userId = auth.getCurrentUser().getUid();

        loadUserProfile();

//        btnLocation.setOnClickListener(v -> fetchCurrentLocation());

        changeImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> updateUserProfile());
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadUserProfile() {
        progressDialog.setMessage("Loading profile...");
        progressDialog.setCancelable(true); // Allow user to cancel with back button

        // If the user cancels the dialog, finish the activity
        progressDialog.setOnCancelListener(dialog -> {
            Toast.makeText(this, "Cancelled. Closing profile...", Toast.LENGTH_SHORT).show();
            finish(); // Closes the current activity
        });

        progressDialog.show();

        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (progressDialog.isShowing()) progressDialog.dismiss();

                    if (documentSnapshot.exists()) {
                        etName.setText(documentSnapshot.getString("name"));
                        edtContact.setText(documentSnapshot.getString("ContactNumber"));
                        newContact = documentSnapshot.getString("ContactNumber");
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

    private void fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1001);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        updatedLatitude = location.getLatitude();
                        updatedLongitude = location.getLongitude();
                        btnLocation.setText("Lat: " + updatedLatitude + ", Lon: " + updatedLongitude);
                        Toast.makeText(this, "Location Updated!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Unable to get location. Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUserProfile() {
        String newName = etName.getText().toString().trim();
        if (newName.isEmpty()) {
            etName.setError("Name is required");
            etName.requestFocus();
            return;
        }
        newContact = edtContact.getText().toString().trim();
        if (newContact.isEmpty()) {
            newContact="98xxxxxxxx";
            return;
        }
        // Remove "Lat: " and "Lon: " using split and trim
        String locationText = btnLocation.getText().toString();
        String[] parts = locationText.split(",");
        String latPart = parts[0].trim().replace("Lat:", "").trim();
        String lonPart = parts[1].trim().replace("Lon:", "").trim();

        updatedLatitude = Double.parseDouble(latPart);
        updatedLongitude = Double.parseDouble(lonPart);


        progressDialog.show();

        if (imageUri != null) {
            uploadImageToStorage(newName);
        } else {
            updateFirestore(newName, null,newContact);
        }
    }

    private void uploadImageToStorage(String name) {
        try {
            // Step 1: Get bitmap from URI
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

            // Step 2: Convert and compress to JPEG format only
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            boolean isCompressed = bitmap.compress(Bitmap.CompressFormat.JPEG, 35, baos);

            if (!isCompressed) {
                Toast.makeText(this, "Image compression failed!", Toast.LENGTH_SHORT).show();
                return;
            }

            byte[] compressedData = baos.toByteArray();

            // Step 3: Define storage path with .jpg extension
            StorageReference imageRef = storage.getReference().child("profileImages/" + userId + ".jpg");

            // Step 4: Upload the JPEG data
            UploadTask uploadTask = imageRef.putBytes(compressedData);

            uploadTask.addOnSuccessListener(taskSnapshot ->
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        updateFirestore(name, uri.toString(), newContact);
                    })
            ).addOnFailureListener(e ->{
                Toast.makeText(this, "Failed to upload image!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                    }

            );

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Image processing error!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    private void updateFirestore(String name, String imageUrl,String contactnumber) {
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("name", name);
        updatedData.put("latitude", updatedLatitude);
        updatedData.put("longitude", updatedLongitude);
        updatedData.put("ContactNumber",contactnumber);
        if (imageUrl != null) {
            updatedData.put("profileImage", imageUrl);
        }

// Perform the Firestore update
        db.collection("users").document(userId).update(updatedData)
                .addOnSuccessListener(aVoid -> {
                    // Dismiss the progress dialog upon success
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        startActivity(new Intent(UserProfile.this,MainActivity.class));
                        finish();
                    }
                    Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show();

                })
                .addOnFailureListener(e -> {
                    // Dismiss the progress dialog upon failure
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(this, "Failed to update profile!", Toast.LENGTH_SHORT).show();
                });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
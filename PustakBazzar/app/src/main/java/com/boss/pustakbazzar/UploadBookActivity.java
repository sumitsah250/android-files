package com.boss.pustakbazzar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UploadBookActivity extends AppCompatActivity {
    private EditText etBookTitle, etBookAuthor, etBookPrice, etBookDescription;
    private Button btnUploadImage, btnSubmitBook;
    private ImageView imgBookPreview;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private Uri imageUri;
    private String userId;

    private Dialog progressDialog; // Progress Dialog

    private static final int IMAGE_PICK_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_book);

        etBookTitle = findViewById(R.id.etBookTitle);
        etBookAuthor = findViewById(R.id.etBookAuthor);
        etBookPrice = findViewById(R.id.etBookPrice);
        etBookDescription = findViewById(R.id.etBookDescription);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        imgBookPreview = findViewById(R.id.imgBookPreview);
        btnSubmitBook = findViewById(R.id.btnSubmitBook);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        btnUploadImage.setOnClickListener(v -> openImagePicker());
        btnSubmitBook.setOnClickListener(v -> submitBookDetails());
        initProgressDialog();
        findViewById(R.id.btnback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            progressDialog.show(); // Show progress dialog while loading image
            imageUri = data.getData();

            // Load image in a separate thread to prevent blocking UI
            new Thread(() -> {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    // Update UI on main thread
                    runOnUiThread(() -> {
                        imgBookPreview.setImageBitmap(bitmap);
                        progressDialog.dismiss(); // Hide progress dialog after image is loaded
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                    });
                }
            }).start();
        }
    }


    private void submitBookDetails() {
        String title = etBookTitle.getText().toString().trim();
        String author = etBookAuthor.getText().toString().trim();
        String price = etBookPrice.getText().toString().trim();
        String description = etBookDescription.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty() || price.isEmpty() || description.isEmpty() || imageUri == null) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show(); // Show progress dialog
        btnSubmitBook.setEnabled(false); // Prevent multiple clicks

        String bookId = UUID.randomUUID().toString();
        compressAndUploadImage(bookId, title, author, price, description);
    }

    private void compressAndUploadImage(String bookId, String title, String author, String price, String description) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos); // Compress to 50% quality
            byte[] compressedData = baos.toByteArray();

            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("book_images").child(bookId);
            storageRef.putBytes(compressedData)
                    .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                Map<String, Object> book = new HashMap<>();
                                book.put("id", bookId);
                                book.put("title", title);
                                book.put("author", author);
                                book.put("price", price);
                                book.put("description", description);
                                book.put("imageUrl", uri.toString());
                                book.put("sellerEmail", auth.getCurrentUser().getEmail());
                                book.put("userId", userId);

                                db.collection("books").document(bookId)
                                        .set(book)
                                        .addOnSuccessListener(aVoid -> {
                                            progressDialog.dismiss(); // Hide progress dialog
                                            btnSubmitBook.setEnabled(true); // Re-enable button
                                            Intent intent = new Intent(UploadBookActivity.this, MainActivity.class);
                                            intent.putExtra("bookId", bookId);
                                            startActivity(intent);
                                            Toast.makeText(UploadBookActivity.this, "Book uploaded successfully", Toast.LENGTH_SHORT).show();
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            progressDialog.dismiss();
                                            btnSubmitBook.setEnabled(true);
                                            Toast.makeText(this, "Failed to upload book", Toast.LENGTH_SHORT).show();
                                        });
                            }))
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        btnSubmitBook.setEnabled(true);
                        Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show();
                    });
        } catch (IOException e) {
            progressDialog.dismiss();
            btnSubmitBook.setEnabled(true);
            e.printStackTrace();
            Toast.makeText(this, "Failed to compress image", Toast.LENGTH_SHORT).show();
        }
    }

    private void initProgressDialog() {
        progressDialog = new Dialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.custom_progress_dialog);
        progressDialog.setCancelable(false);
    }
}

package com.boss.pustakbazzar;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UploadBookActivity extends AppCompatActivity {
    private EditText etBookTitle, etBookAuthor, etBookPrice, etBookDescription;
    private Button btnUploadImage, btnSubmitBook;
    private static final int IMAGE_PICK_CODE = 1000;
    private ImageView imgBookPreview;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private Uri imageUri;
    private String userId;

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
        userId = auth.getCurrentUser().getUid(); // Get the current user ID

        btnUploadImage.setOnClickListener(v -> openImagePicker());

        btnSubmitBook.setOnClickListener(v -> submitBookDetails());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imgBookPreview.setImageURI(imageUri);  // Preview the selected image
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
        // Generate a unique bookId (can use UUID or current timestamp)
        String bookId = UUID.randomUUID().toString();  // Or you can use System.currentTimeMillis() for a unique timestamp-based ID

// Upload the image to Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("book_images").child(bookId); // Use bookId for image storage reference
        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl()
                        .addOnSuccessListener(uri -> {
                            // Save book details in Firestore using the pre-generated bookId
                            Map<String, Object> book = new HashMap<>();
                            book.put("id", bookId);  // Add the bookId to the Firestore document
                            book.put("title", title);
                            book.put("author", author);
                            book.put("price", price);
                            book.put("description", description);
                            book.put("imageUrl", uri.toString());
                            book.put("sellerEmail", auth.getCurrentUser().getEmail());
                            book.put("userId", userId);

                            // Store the book details in Firestore with the custom bookId
                            db.collection("books").document(bookId)  // Use bookId as the document ID
                                    .set(book)  // Use set() instead of add() to set the document with a specific ID
                                    .addOnSuccessListener(aVoid -> {
                                        // Passing the bookId to MainActivity or wherever necessary
                                        Intent intent = new Intent(UploadBookActivity.this, MainActivity.class);
                                        intent.putExtra("bookId", bookId); // Pass the bookId to MainActivity
                                        startActivity(intent);

                                        // Optionally go back to the previous activity
                                        Toast.makeText(UploadBookActivity.this, "Book uploaded successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to upload book", Toast.LENGTH_SHORT).show());
                        }))
                .addOnFailureListener(e -> Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show());
/////////////////////////////////////

//        // Upload image to Firebase Storage
//        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("book_images").child(UUID.randomUUID().toString());
//        storageRef.putFile(imageUri)
//                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl()
//                        .addOnSuccessListener(uri -> {
//                            // Save book details in Firestore
//                            Map<String, Object> book = new HashMap<>();
//                            book.put("title", title);
//                            book.put("author", author);
//                            book.put("price", price);
//                            book.put("description", description);
//                            book.put("imageUrl", uri.toString());
//                            book.put("sellerEmail", auth.getCurrentUser().getEmail());
//                            book.put("userId", userId);
//
//                            // Add the book to Firestore and retrieve the document ID (bookId)
//                            db.collection("books").add(book)
//                                    .addOnSuccessListener(documentReference -> {
//                                        String bookId = documentReference.getId(); // Get the bookId
//                                        // Passing the bookId to MainActivity or wherever necessary
//                                        Intent intent = new Intent(UploadBookActivity.this, MainActivity.class);
//                                        intent.putExtra("bookId", bookId); // Pass bookId to MainActivity
//                                        startActivity(intent);
//
//                                        // Optionally go back to the previous activity
//                                        Toast.makeText(UploadBookActivity.this, "Book uploaded successfully", Toast.LENGTH_SHORT).show();
//                                        finish();
//                                    })
//                                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to upload book", Toast.LENGTH_SHORT).show());
//                        }))
//                .addOnFailureListener(e -> Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show());

    }


}
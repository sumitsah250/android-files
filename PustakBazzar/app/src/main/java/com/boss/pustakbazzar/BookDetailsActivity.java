package com.boss.pustakbazzar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class BookDetailsActivity extends AppCompatActivity {
    private ImageView imgBook;
    private TextView textTitle, textAuthor, textPrice, textDescription, textSellerContact;
    private Button btnContactSeller;
    private FirebaseFirestore db;
    private String bookId, sellerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_details);
        imgBook = findViewById(R.id.imgBook);
        textTitle = findViewById(R.id.textTitle);
        textAuthor = findViewById(R.id.textAuthor);
        textPrice = findViewById(R.id.textPrice);
        textDescription = findViewById(R.id.textDescription);
        textSellerContact = findViewById(R.id.textSellerContact);
        btnContactSeller = findViewById(R.id.btnContactSeller);

        db = FirebaseFirestore.getInstance();

        // Get the bookId from intent
        bookId = getIntent().getStringExtra("bookId");

        if (bookId != null) {
            loadBookDetails(bookId);
        }

        // When the "Contact Seller" button is clicked, open email client
        btnContactSeller.setOnClickListener(v -> contactSeller());
    }

    private void loadBookDetails(String bookId) {
        db.collection("books").document(bookId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String title = documentSnapshot.getString("title");
                        String author = documentSnapshot.getString("author");
                        String price = documentSnapshot.getString("price");
                        String description = documentSnapshot.getString("description");
                        String imageUrl = documentSnapshot.getString("imageUrl");
                        sellerEmail = documentSnapshot.getString("sellerEmail");
                        String sellerContact = documentSnapshot.getString("sellerContact");

                        textTitle.setText(title);
                        textAuthor.setText("Author: " + author);
                        textPrice.setText("Price: ₹" + price);
                        textDescription.setText(description);
                        textSellerContact.setText("Seller Contact: " + sellerContact);

                        // Load the book image using Picasso
                        Picasso.get().load(imageUrl).into(imgBook);
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error loading book details!", Toast.LENGTH_SHORT).show());
    }

    // Method to contact the seller via email
    private void contactSeller() {
        if (sellerEmail != null) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{sellerEmail});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry about your book");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, I'm interested in buying your book. Please let me know the details.");
            try {
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "No email client installed.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Seller contact unavailable!", Toast.LENGTH_SHORT).show();
        }
    }
}
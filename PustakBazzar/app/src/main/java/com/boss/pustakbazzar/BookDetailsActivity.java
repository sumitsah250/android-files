package com.boss.pustakbazzar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BookDetailsActivity extends AppCompatActivity {
    private ImageView imgBook;
    private TextView textTitle, textAuthor, textPrice, textDescription;
    private Button btnContactSeller;
    ImageButton btnback;
    private FirebaseFirestore db;
    private String bookId, sellerEmail;
    private Dialog progressDialog; // Progress Dialog
    String userid;

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
        btnback=findViewById(R.id.btnback);

        btnContactSeller = findViewById(R.id.btnContactSeller);

        db = FirebaseFirestore.getInstance();

        initProgressDialog(); // Initialize Progress Dialog

        // Get the bookId from intent
        bookId = getIntent().getStringExtra("bookId");

        if (bookId != null) {
            loadBookDetails(bookId);
        }

        // When the "Contact Seller" button is clicked, open email client
        btnContactSeller.setOnClickListener(v -> contactSeller());
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadBookDetails(String bookId) {
        progressDialog.show(); // Show progress dialog while loading details
        btnContactSeller.setText("Wait..."); // Change button text to "Wait"

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
                        userid=documentSnapshot.getString("userId");

                        textTitle.setText(title);
                        textAuthor.setText("Author: " + author);
                        textPrice.setText("Price: ₹" + price);
                        textDescription.setText(description);

                        // Load book image with progress dialog
                        loadImageWithProgress(imageUrl);
                    } else {
                        progressDialog.dismiss();
                        btnContactSeller.setText("Contact Seller"); // Restore button text
                        Toast.makeText(this, "Book details not found!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    btnContactSeller.setText("Contact Seller"); // Restore button text
                    Toast.makeText(this, "Error loading book details!", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadImageWithProgress(String imageUrl) {
        progressDialog.show(); // Show progress dialog before loading image

        Picasso.get().load(imageUrl)
                .into(imgBook, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss(); // Dismiss progress dialog when image is loaded
                        btnContactSeller.setText("Contact Seller"); // Restore button text
                    }

                    @Override
                    public void onError(Exception e) {
                        progressDialog.dismiss();
                        btnContactSeller.setText("Contact Seller"); // Restore button text
                        Toast.makeText(BookDetailsActivity.this, "Failed to load image!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Method to contact the seller via email
    private void contactSeller() {
        Intent intent = new Intent(BookDetailsActivity.this, SellerDetails.class);
        intent.putExtra("userid", userid); // send string

        startActivity(intent);



//        if (sellerEmail != null) {
//            Intent emailIntent = new Intent(Intent.ACTION_SEND);
//            emailIntent.setType("message/rfc822");
//            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{sellerEmail});
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry about your book");
//            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, I'm interested in buying your book. Please let me know the details.");
//            try {
//                startActivity(Intent.createChooser(emailIntent, "Send Email"));
//            } catch (android.content.ActivityNotFoundException ex) {
//                Toast.makeText(this, "No email client installed.", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(this, "Seller contact unavailable!", Toast.LENGTH_SHORT).show();
//        }
    }

    // Initialize Progress Dialog
    private void initProgressDialog() {
        progressDialog = new Dialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.custom_progress_dialog);
        progressDialog.setCancelable(false);
        TextView progressText = progressDialog.findViewById(R.id.progress_text);
        progressText.setText("Loading..."); // Set the text to "Loading..."
    }
}

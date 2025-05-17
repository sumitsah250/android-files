package com.boss.pustakbazzar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MineBook extends AppCompatActivity {
    private BookAdapter adapter;
    private List<Book> bookList, filteredList;

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String userId;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mine_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView=findViewById(R.id.recyclerView);


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        recyclerView.setLayoutManager(new GridLayoutManager(MineBook.this,2));

        bookList = new ArrayList<>();
        filteredList = new ArrayList<>();
        userId = auth.getCurrentUser().getUid();
        adapter = new BookAdapter(filteredList, MineBook.this,userId);
        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        loadBooks();
        findViewById(R.id.btnback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void loadBooks() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Fetch current user's location first
        db.collection("users").document(currentUserId).get().addOnSuccessListener(userDoc -> {
            if (userDoc.exists()) {
                double currentLat = userDoc.getDouble("latitude");
                double currentLng = userDoc.getDouble("longitude");

                // Query only books uploaded by current user
                db.collection("books")
                        .whereEqualTo("userId", currentUserId)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            bookList.clear();

                            final int totalBooks = queryDocumentSnapshots.size();
                            final int[] processedCount = {0};

                            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                                Book book = doc.toObject(Book.class);

                                // Since seller is current user, use current location for both points
                                double distance = calculateDistance(currentLat, currentLng, currentLat, currentLng);
                                book.setDistance(distance);

                                bookList.add(book);

                                processedCount[0]++;
                                if (processedCount[0] == totalBooks) {
                                    // Sort books by distance (all zero actually)
                                    Collections.sort(bookList, (b1, b2) -> Double.compare(b1.getDistance(), b2.getDistance()));

                                    filteredList.clear();
                                    filteredList.addAll(bookList);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            // Handle case: no books found
                            if (totalBooks == 0) {
                                filteredList.clear();
                                adapter.notifyDataSetChanged();
                            }

                        })
                        .addOnFailureListener(e -> Toast.makeText(this, "Error loading books!", Toast.LENGTH_SHORT).show());

            } else {
                Toast.makeText(this, "User location not found", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(this, "Failed to get user location", Toast.LENGTH_SHORT).show());
    }

    // Method to calculate distance between two lat/lng points (reuse your adapter's method)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        android.location.Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0] / 1000.0; // km
    }

}
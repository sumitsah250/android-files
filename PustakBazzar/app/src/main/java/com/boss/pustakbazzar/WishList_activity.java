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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WishList_activity extends AppCompatActivity {
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
        setContentView(R.layout.activity_wish_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView=findViewById(R.id.recyclerView);


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        recyclerView.setLayoutManager(new GridLayoutManager(WishList_activity.this,2));

        bookList = new ArrayList<>();
        filteredList = new ArrayList<>();
        userId = auth.getCurrentUser().getUid();
        adapter = new BookAdapter(filteredList, WishList_activity.this,userId);
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
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users").document(userId)
                .get()
                .addOnSuccessListener(userSnapshot -> {
                    List<String> wishlist = (List<String>) userSnapshot.get("wishlist");

                    if (wishlist == null || wishlist.isEmpty()) {
                        bookList.clear();
                        filteredList.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "No books in wishlist.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Fetch current user's location first
                    Double currentLat = userSnapshot.getDouble("latitude");
                    Double currentLng = userSnapshot.getDouble("longitude");

                    if (currentLat == null || currentLng == null) {
                        Toast.makeText(this, "User location not available.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    List<List<String>> chunks = chunkList(wishlist, 10);
                    bookList.clear();

                    // To handle all async tasks counting
                    final int totalChunks = chunks.size();
                    final int[] chunksProcessed = {0};

                    for (List<String> chunk : chunks) {
                        FirebaseFirestore.getInstance().collection("books")
                                .whereIn("id", chunk)
                                .get()
                                .addOnSuccessListener(querySnapshots -> {
                                    List<Book> chunkBooks = new ArrayList<>();

                                    final int totalBooksInChunk = querySnapshots.size();
                                    final int[] booksProcessed = {0};

                                    if (totalBooksInChunk == 0) {
                                        // If no books in this chunk, update chunksProcessed
                                        chunksProcessed[0]++;
                                        checkAndUpdateAdapter(chunksProcessed[0], totalChunks);
                                        return;
                                    }

                                    for (DocumentSnapshot doc : querySnapshots) {
                                        Book book = doc.toObject(Book.class);

                                        // Fetch seller location
                                        FirebaseFirestore.getInstance().collection("users").document(book.getUserId())
                                                .get()
                                                .addOnSuccessListener(sellerDoc -> {
                                                    if (sellerDoc.exists()) {
                                                        Double sellerLat = sellerDoc.getDouble("latitude");
                                                        Double sellerLng = sellerDoc.getDouble("longitude");

                                                        if (sellerLat != null && sellerLng != null) {
                                                            double distance = calculateDistance(currentLat, currentLng, sellerLat, sellerLng);
                                                            book.setDistance(distance);
                                                        } else {
                                                            book.setDistance(Double.MAX_VALUE);
                                                        }
                                                    } else {
                                                        book.setDistance(Double.MAX_VALUE);
                                                    }

                                                    chunkBooks.add(book);
                                                    booksProcessed[0]++;

                                                    if (booksProcessed[0] == totalBooksInChunk) {
                                                        bookList.addAll(chunkBooks);
                                                        chunksProcessed[0]++;
                                                        checkAndUpdateAdapter(chunksProcessed[0], totalChunks);
                                                    }
                                                })
                                                .addOnFailureListener(e -> {
                                                    book.setDistance(Double.MAX_VALUE);
                                                    chunkBooks.add(book);
                                                    booksProcessed[0]++;

                                                    if (booksProcessed[0] == totalBooksInChunk) {
                                                        bookList.addAll(chunkBooks);
                                                        chunksProcessed[0]++;
                                                        checkAndUpdateAdapter(chunksProcessed[0], totalChunks);
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Error loading wishlist books!", Toast.LENGTH_SHORT).show();
                                    chunksProcessed[0]++;
                                    checkAndUpdateAdapter(chunksProcessed[0], totalChunks);
                                });
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to load wishlist!", Toast.LENGTH_SHORT).show());
    }

    // Helper to check if all chunks processed then update UI
    private void checkAndUpdateAdapter(int chunksDone, int totalChunks) {
        if (chunksDone == totalChunks) {
            // Sort books by distance ascending
            Collections.sort(bookList, (b1, b2) -> Double.compare(b1.getDistance(), b2.getDistance()));

            filteredList.clear();
            filteredList.addAll(bookList);
            adapter.notifyDataSetChanged();
        }
    }

    // Your existing chunkList method remains unchanged
    private List<List<String>> chunkList(List<String> list, int chunkSize) {
        List<List<String>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i += chunkSize) {
            chunks.add(new ArrayList<>(list.subList(i, Math.min(i + chunkSize, list.size()))));
        }
        return chunks;
    }

    // Distance calculation (reuse)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        android.location.Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0] / 1000.0; // kilometers
    }


}
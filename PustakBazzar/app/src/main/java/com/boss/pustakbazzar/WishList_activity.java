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

                    // Firestore supports a maximum of 10 elements in whereIn()
                    List<List<String>> chunks = chunkList(wishlist, 10);

                    bookList.clear(); // Clear existing data

                    for (List<String> chunk : chunks) {
                        FirebaseFirestore.getInstance().collection("books")
                                .whereIn("id", chunk)  // Ensure each book has a `bookId` field
                                .get()
                                .addOnSuccessListener(querySnapshots -> {
                                    for (DocumentSnapshot doc : querySnapshots) {
                                        Book book = doc.toObject(Book.class);
                                        bookList.add(book);
                                    }

                                    filteredList.clear();
                                    filteredList.addAll(bookList);
                                    adapter.notifyDataSetChanged();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "Error loading wishlist books!", Toast.LENGTH_SHORT).show());
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to load wishlist!", Toast.LENGTH_SHORT).show());


}
    private List<List<String>> chunkList(List<String> list, int chunkSize) {
        List<List<String>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i += chunkSize) {
            chunks.add(new ArrayList<>(list.subList(i, Math.min(i + chunkSize, list.size()))));
        }
        return chunks;
    }


}
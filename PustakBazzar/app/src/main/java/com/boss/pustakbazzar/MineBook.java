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
        db.collection("books")
                .whereEqualTo("userId", userId) // Match only books uploaded by current user
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    bookList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Book book = doc.toObject(Book.class);
                        bookList.add(book);
                    }

                    filteredList.clear();
                    filteredList.addAll(bookList);
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error loading books!", Toast.LENGTH_SHORT).show()
                );
    }

}
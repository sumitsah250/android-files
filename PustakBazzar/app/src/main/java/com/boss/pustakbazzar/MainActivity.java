package com.boss.pustakbazzar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.pustakbazzar.databinding.ActivityMainBinding;
import com.boss.pustakbazzar.databinding.MainActivityContentBinding;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding1;
    MainActivityContentBinding binding;
//    private EditText searchBox;
//    private Button btnGoToUpload;
//    private RecyclerView recyclerView;

    private BookAdapter adapter;
    private List<Book> bookList, filteredList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding1 = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding1.getRoot());

        // for the side menu
        binding1.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if(itemId==R.id.nav_home){
                    binding1.main.closeDrawer(GravityCompat.START);
                }
                if(itemId==R.id.privacy){
                    openPrivacyPolicy();
                }
                if(itemId==R.id.rate){
                    showRateApp();
                }
                if(itemId==R.id.seeallapps){
                    openPlayStore();
                }
                if(itemId==R.id.share){
                    shareApp();
                }
                if(itemId==R.id.logout){
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    // Sign out the user
                    firebaseAuth.signOut();
                    // Optionally, you can navigate the user to a login screen after logout
                    Intent loginIntent = new Intent(MainActivity.this ,activity_login.class);
                    startActivity(loginIntent);
                    finish(); // Finish the current activity so that the user cannot go back to it
                }

                return false;
            }
        });
        // for the side menu


        binding = MainActivityContentBinding.bind(binding1.includedLayout.getRoot());

        binding.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding1.main.open();
            }
        });

//        searchBox = findViewById(R.id.searchBox);
//        recyclerView = findViewById(R.id.recyclerView);
//        btnGoToUpload = findViewById(R.id.btnUploadImage);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new BookAdapter(filteredList, this);
        binding.recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadBooks();

        binding.btnUploadImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UploadBookActivity.class);
            startActivity(intent);
        });

        binding.searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterBooks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadBooks() {
        db.collection("books").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    bookList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Book book = doc.toObject(Book.class);
                        bookList.add(book);
                    }
                    filterBooks(binding.searchBox.getText().toString()); // Apply filter when data is loaded
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error loading books!", Toast.LENGTH_SHORT).show());
    }

    private void filterBooks(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(bookList);
        } else {
            for (Book book : bookList) {
                if (book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(book);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
    // for side menu
    public void showRateApp() {
        // Create a ReviewManager instance
        ReviewManager reviewManager = ReviewManagerFactory.create(this);

        // Request a ReviewInfo object
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();

        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Retrieve the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                // Launch the review flow
                Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(flowTask -> {
                    // Optionally handle the completion of the review flow
                    Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                });
            } else {
                // Handle the error (e.g., log it, display a message to the user)
                Toast.makeText(this, "Unable to show review dialog at this time.", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void openPlayStore() {
        String developerPageUrl = "https://play.google.com/store/apps/developer?id=SUMIX DEVELOPERS";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(developerPageUrl));
        startActivity(intent);
    }
    private void shareApp() {
        String appPackageName = "com.boss.class10allguidemanual2081"; // Replace with your app's package name
        String appUrl = "https://play.google.com/store/apps/details?id=" + appPackageName;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app!"); // Title of the message
        shareIntent.putExtra(Intent.EXTRA_TEXT, "I recommend this app: " + appUrl); // Body of the message

        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
    private void openPrivacyPolicy() {
        String privacyPolicyUrl = "https://www.termsfeed.com/live/65ff51e3-dbe5-4f79-9d0f-0e98bc7a4a85"; // Replace with your actual URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl));
        startActivity(intent);
    }

    // for side menu
}
package com.boss.pustakbazzar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.boss.pustakbazzar.databinding.ActivityMainBinding;
import com.boss.pustakbazzar.databinding.MainActivityContentBinding;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding1;
    MainActivityContentBinding binding;

    private BookAdapter adapter;
    private List<Book> bookList;

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String userId;

    private DocumentSnapshot lastVisibleBook = null;
    private boolean isLoadingBooks = false;
    private static final int PAGE_SIZE = 20;
    private String currentSearchQuery = "";

    View headerView;
    CircleImageView profileImage;
    TextView username;
    TextView usergmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding1 = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding1.getRoot());

        //to turn off dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //to turn off the dark mode




        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        userId = auth.getCurrentUser().getUid();

        headerView = binding1.navigationView.getHeaderView(0);
        profileImage = headerView.findViewById(R.id.profileimage);
        username = headerView.findViewById(R.id.username);
        usergmail=headerView.findViewById(R.id.gmailText);

        loadUserProfile();

        headerView.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, UserProfile.class)));

        binding1.navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                binding1.main.closeDrawer(GravityCompat.START);
            }
            if (itemId == R.id.privacy) {
                openPrivacyPolicy();
            }
            if (itemId == R.id.rate) {
                showRateApp();
            }
            if (itemId == R.id.seeallapps) {
                openPlayStore();
            }
            if (itemId == R.id.share) {
                shareApp();
            }
            if (itemId == R.id.logout) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, activity_login.class));
                finish();
            }
            return false;
        });

        binding = MainActivityContentBinding.bind(binding1.includedLayout.getRoot());

        binding.appCompatMyBooks.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MineBook.class)));
        binding.appCompatButtonWishList.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, WishList_activity.class)));

        binding.toggle.setOnClickListener(v -> binding1.main.open());

        binding.recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        bookList = new ArrayList<>();
        adapter = new BookAdapter(bookList, MainActivity.this, userId);
        binding.recyclerView.setAdapter(adapter);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager == null) return;

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoadingBooks && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 5
                        && firstVisibleItemPosition >= 0) {
                    loadBooks(currentSearchQuery);
                }
            }
        });

        binding.btnUploadImage.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, UploadBookActivity.class)));

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            lastVisibleBook = null;
            bookList.clear();
            adapter.notifyDataSetChanged();
            loadBooks(currentSearchQuery);
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        binding.searchBox.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearchQuery = s.toString().trim();
                lastVisibleBook = null;
                bookList.clear();
                adapter.notifyDataSetChanged();
                loadBooks(currentSearchQuery);
            }
        });

        // Load initial books with empty search
        loadBooks("");
    }

    private void loadBooks(String query) {
        if (isLoadingBooks) return;
        isLoadingBooks = true;

        Query baseQuery = db.collection("books")
                .orderBy("title")
                .limit(PAGE_SIZE);

        if (!query.isEmpty()) {
            String endQuery = query + "\uf8ff";
            baseQuery = db.collection("books")
                    .orderBy("title")
                    .whereGreaterThanOrEqualTo("title", query)
                    .whereLessThanOrEqualTo("title", endQuery)
                    .limit(PAGE_SIZE);
        }

        if (lastVisibleBook != null) {
            baseQuery = baseQuery.startAfter(lastVisibleBook);
        }

        baseQuery.get().addOnSuccessListener(querySnapshot -> {
            if (!querySnapshot.isEmpty()) {
                if (lastVisibleBook == null) {
                    bookList.clear();
                }

                List<Book> tempList = new ArrayList<>();
                for (DocumentSnapshot doc : querySnapshot) {
                    Book book = doc.toObject(Book.class);
                    tempList.add(book);
                }

                lastVisibleBook = querySnapshot.getDocuments().get(querySnapshot.size() - 1);

                // Get current user's location
                db.collection("users").document(userId).get().addOnSuccessListener(userDoc -> {
                    if (userDoc.exists()) {
                        Double currentLat = userDoc.getDouble("latitude");
                        Double currentLng = userDoc.getDouble("longitude");

                        // Offload distance calculation and sorting to background thread
                        new Thread(() -> {
                            if (currentLat != null && currentLng != null) {
                                for (Book book : tempList) {
                                    Double sellerLat = book.getLatitude();
                                    Double sellerLng = book.getLongitude();
                                    if (sellerLat != null && sellerLng != null) {
                                        double dist = calculateDistance(currentLat, currentLng, sellerLat, sellerLng);
                                        book.setDistance(dist);
                                    } else {
                                        book.setDistance(Double.MAX_VALUE);
                                    }
                                }
                                Collections.sort(tempList, Comparator.comparingDouble(Book::getDistance));
                            }

                            // Update UI on main thread
                            runOnUiThread(() -> {
                                bookList.addAll(tempList);
                                adapter.notifyDataSetChanged();
                                isLoadingBooks = false;
                            });
                        }).start();

                    } else {
                        // Fallback if user location not found
                        bookList.addAll(tempList);
                        adapter.notifyDataSetChanged();
                        isLoadingBooks = false;
                    }
                }).addOnFailureListener(e -> {
                    bookList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                    isLoadingBooks = false;
                });

            } else {
                isLoadingBooks = false;
            }
        }).addOnFailureListener(e -> {
            isLoadingBooks = false;
            Toast.makeText(this, "Error loading books!", Toast.LENGTH_SHORT).show();
        });
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        android.location.Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0] / 1000.0; // in km
    }

    // Side menu functions
    public void showRateApp() {
        ReviewManager reviewManager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ReviewInfo reviewInfo = task.getResult();
                Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(flowTask ->
                        Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(this, "Unable to show review dialog at this time.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openPlayStore() {
        String developerPageUrl = "https://play.google.com/store/apps/developer?id=SUMIX DEVELOPERS";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(developerPageUrl)));
    }

    private void shareApp() {
        String appPackageName = "com.boss.class10allguidemanual2081";
        String appUrl = "https://play.google.com/store/apps/details?id=" + appPackageName;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app!");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "I recommend this app: " + appUrl);

        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    private void openPrivacyPolicy() {
        String privacyPolicyUrl = "https://www.termsfeed.com/live/65ff51e3-dbe5-4f79-9d0f-0e98bc7a4a85";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl)));
    }

    private void loadUserProfile() {
        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        username.setText(documentSnapshot.getString("name"));
                        usergmail.setText(documentSnapshot.getString("email"));
                        String imageUrl = documentSnapshot.getString("profileImage");

                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            if (!isDestroyed() && !isFinishing()) { // Prevent crash
                                Glide.with(this)
                                        .load(imageUrl)
                                        .into(profileImage);
                            }
                        }
                    }
                });
    }

}

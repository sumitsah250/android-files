package com.boss.pustakbazzar;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;
    private Context context;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String currentUserId;

    // Constructor to initialize the adapter
    public BookAdapter(List<Book> bookList, Context context, String currentUserId) {
        this.bookList = bookList;
        this.context = context;
        this.currentUserId = currentUserId;
        this.db = FirebaseFirestore.getInstance();
        this.auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.textTitle.setText(book.getTitle());
//        holder.textAuthor.setText(book.getAuthor());
        holder.textPrice.setText("₹" + book.getPrice());
        Picasso.get().load(book.getImageUrl()).into(holder.imgBook);

        // Fetch current user's location
        fetchUserLocation(currentUserId, new LocationCallback() {
            @Override
            public void onLocationFetched(double currentUserLat, double currentUserLng) {
                // Fetch seller's location using the book's userId
                fetchUserLocation(book.getUserId(), new LocationCallback() {
                    @Override
                    public void onLocationFetched(double sellerLat, double sellerLng) {
                        // Calculate the distance
                        double distance = calculateDistance(currentUserLat, currentUserLng, sellerLat, sellerLng);
                        holder.textDistance.setText(String.format("📍%.1f km", distance));

                        // Add distance to the book object (optional, for sorting purposes)
                        book.setDistance(distance);
                    }
                });
            }
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookDetailsActivity.class);
            intent.putExtra("bookId", book.getId());
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            // Show delete confirmation dialog
            showDeleteConfirmationDialog(book.getId(), position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textAuthor, textPrice, textDistance;
        ImageView imgBook;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textAuthor = itemView.findViewById(R.id.textAuthor);
            textPrice = itemView.findViewById(R.id.textPrice);
            textDistance = itemView.findViewById(R.id.textDistance);  // Distance TextView
            imgBook = itemView.findViewById(R.id.imgBook);
        }
    }

    // Method to fetch the user's location from Firebase
    private void fetchUserLocation(String userId, final LocationCallback callback) {
        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        double latitude = documentSnapshot.getDouble("latitude");
                        double longitude = documentSnapshot.getDouble("longitude");
                        callback.onLocationFetched(latitude, longitude);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to fetch user location", Toast.LENGTH_SHORT).show();
                });
    }

    // Callback interface to handle location fetching
    interface LocationCallback {
        void onLocationFetched(double latitude, double longitude);
    }

    // Method to calculate the distance between two geographical points
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0] / 1000.0; // Convert to kilometers
    }

    // Method to sort books by distance
    public void sortBooksByDistance() {
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book book1, Book book2) {
                return Double.compare(book1.getDistance(), book2.getDistance());
            }
        });
        notifyDataSetChanged();  // Notify the adapter that the data set has changed
    }

    // Method to show the delete confirmation dialog
    private void showDeleteConfirmationDialog(String bookId, int position) {
        new android.app.AlertDialog.Builder(context)
                .setTitle("Delete Book")
                .setMessage("Are you sure you want to delete this book?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    // Proceed to delete the book if user confirms
                    deleteBook(bookId, position);
                })
                .setNegativeButton("No", (dialog, id) -> {
                    // Dismiss the dialog if user cancels
                    dialog.dismiss();
                })
                .show();
    }

    // Method to delete the book from Firestore
    private void deleteBook(String bookId, int position) {
        db.collection("books").document(bookId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Book deleted successfully", Toast.LENGTH_SHORT).show();
                    // Remove the book from the list
                    bookList.remove(position);
                    // Notify the adapter that the item has been removed
                    notifyItemRemoved(position);
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to delete book", Toast.LENGTH_SHORT).show());
    }
}

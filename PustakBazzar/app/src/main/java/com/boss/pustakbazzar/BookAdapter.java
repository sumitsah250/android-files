package com.boss.pustakbazzar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
    private String currentUserId;

    public BookAdapter(List<Book> bookList, Context context, String currentUserId) {
        this.bookList = bookList;
        this.context = context;
        this.currentUserId = currentUserId;
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
        holder.textPrice.setText("Rs." + book.getPrice());
        holder.txtdiscountedprice.setText("Rs."+book.getDiscountedPrice());

        Picasso.get().load(book.getImageUrl()).into(holder.imgBook);

        // Display distance (already calculated and set before passing to adapter)
        holder.textDistance.setText(String.format("📍%.1f km", book.getDistance()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookDetailsActivity.class);
            intent.putExtra("bookId", book.getId());
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            showDeleteConfirmationDialog(book.getId(), position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textPrice, textDistance,txtdiscountedprice;
        ImageView imgBook;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textPrice = itemView.findViewById(R.id.textOriginalPrice);
            textDistance = itemView.findViewById(R.id.textDistance);
            txtdiscountedprice=itemView.findViewById(R.id.textDiscountedPrice);
            imgBook = itemView.findViewById(R.id.imgBook);
        }
    }

    // Sort books by pre-calculated distance
    public void sortBooksByDistance() {
        Collections.sort(bookList, Comparator.comparingDouble(Book::getDistance));
        notifyDataSetChanged();
    }

    // Show delete confirmation dialog
    private void showDeleteConfirmationDialog(String bookId, int position) {
        new android.app.AlertDialog.Builder(context)
                .setTitle("Delete Book")
                .setMessage("Are you sure you want to delete this book?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> deleteBook(bookId, position))
                .setNegativeButton("No", (dialog, id) -> dialog.dismiss())
                .show();
    }

    // Delete book from Firestore
    private void deleteBook(String bookId, int position) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance()
                .collection("books")
                .document(bookId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String bookUserId = documentSnapshot.getString("userId");

                        Log.d("DeleteDebug", "Book userId: " + bookUserId);
                        Log.d("DeleteDebug", "Current userId: " + currentUserId);

                        if (bookUserId != null && currentUserId.trim().equalsIgnoreCase(bookUserId.trim())) {
                            FirebaseFirestore.getInstance()
                                    .collection("books")
                                    .document(bookId)
                                    .delete()
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(context, "Book deleted successfully", Toast.LENGTH_SHORT).show();
                                        bookList.remove(position);
                                        notifyItemRemoved(position);
                                    })
                                    .addOnFailureListener(e ->
                                            Toast.makeText(context, "Failed to delete book", Toast.LENGTH_SHORT).show());
                        } else {
                            Toast.makeText(context, "You are not authorized to delete this book", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Book not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Error fetching book info", Toast.LENGTH_SHORT).show());
    }
}

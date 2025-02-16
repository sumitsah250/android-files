package com.boss.pustakbazzar;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;
    private Context context;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public BookAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
        this.auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.textTitle.setText(book.getTitle());
        holder.textAuthor.setText(book.getAuthor());
        holder.textPrice.setText("₹" + book.getPrice());
        Picasso.get().load(book.getImageUrl()).into(holder.imgBook);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookDetailsActivity.class);
            intent.putExtra("bookId", book.getId());
            context.startActivity(intent);
        });
        holder.itemView.setOnLongClickListener(v -> {
            // Show the delete confirmation dialog when the item is long-pressed
            showDeleteConfirmationDialog(book.getId(),position);
            return true; // Return true to indicate that the long press event was handled
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textAuthor, textPrice;
        ImageView imgBook;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textAuthor = itemView.findViewById(R.id.textAuthor);
            textPrice = itemView.findViewById(R.id.textPrice);
            imgBook = itemView.findViewById(R.id.imgBook);
        }
    }
    private void showDeleteConfirmationDialog(String bookId,int position) {
        new android.app.AlertDialog.Builder(context)
                .setTitle("Delete Book")
                .setMessage("Are you sure you want to delete this book?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    // Proceed to delete the book if user confirms
                    deleteBook(bookId,position);
                })
                .setNegativeButton("No", (dialog, id) -> {
                    // Dismiss the dialog if user cancels
                    dialog.dismiss();
                })
                .show();
    }

    // Method to delete the book from Firestore
    private void deleteBook(String bookId,int position) {
        db.collection("books").document(bookId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Book deleted successfully", Toast.LENGTH_SHORT).show();
                    // Remove the book from the list
                    bookList.remove(position);
                    // Notify the adapter that the item has been removed
                    notifyItemRemoved(position);
                    // Optionally refresh the list or remove the item from the adapter
                    // You can call a method to refresh the RecyclerView or update the list
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to delete book", Toast.LENGTH_SHORT).show());
    }

}

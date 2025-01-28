package com.boss.simplecalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.MarksViewHolder> {

    private ArrayList<Integer> marks;

    public MarksAdapter(ArrayList<Integer> marks) {
        this.marks = marks;
    }

    @NonNull
    @Override
    public MarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mark, parent, false);
        return new MarksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarksViewHolder holder, int position) {
        holder.tvMark.setText("Mark: " + marks.get(position));
    }

    @Override
    public int getItemCount() {
        return marks.size();
    }

    public static class MarksViewHolder extends RecyclerView.ViewHolder {
        TextView tvMark;

        public MarksViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMark = itemView.findViewById(R.id.tvMark);
        }
    }
}

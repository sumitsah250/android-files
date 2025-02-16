package com.boss.cgpacalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.cgpacalculator.CGPAEntry;
import com.boss.cgpacalculator.R;

import java.util.List;

public class CGPAAdapter extends RecyclerView.Adapter<CGPAAdapter.ViewHolder> {

    private List<CGPAEntry> cgpaList;

    public CGPAAdapter(List<CGPAEntry> cgpaList) {
        this.cgpaList = cgpaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cgpa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CGPAEntry entry = cgpaList.get(position);
        holder.cgpaText.setText("CGPA: " + entry.getCgpa());
        holder.creditText.setText("Credit: " + entry.getCredit());
    }

    @Override
    public int getItemCount() {
        return cgpaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cgpaText, creditText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cgpaText = itemView.findViewById(R.id.cgpaText);
            creditText = itemView.findViewById(R.id.creditText);
        }
    }
}

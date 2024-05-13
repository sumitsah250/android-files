package com.example.all_about_recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.all_about_recycler.databinding.RowBinding;

import java.util.ArrayList;

public class recycleradapter_with_binding extends RecyclerView.Adapter<recycleradapter_with_binding.ViewHolder>{
    Context context;
    ArrayList<fruitdetails> arrayList;

    public recycleradapter_with_binding(Context context, ArrayList<fruitdetails> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public recycleradapter_with_binding.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycleradapter_with_binding.ViewHolder holder, int position) {
        holder.binding.rowImage.setImageResource(arrayList.get(position).image);
        holder.binding.rowTitle.setText(arrayList.get(position).title);
        holder.binding.rowDetails.setText(arrayList.get(position).details);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding= RowBinding.bind(itemView);
        }
    }
}

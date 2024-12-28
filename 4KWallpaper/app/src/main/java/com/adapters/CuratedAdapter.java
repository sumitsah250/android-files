package com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.Listners.OnRecyclerClickListtner;
import com.boss.a4kwallpaper.R;
import com.boss.a4kwallpaper.databinding.HomeRecyclerRowBinding;
import com.boss.models.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CuratedAdapter extends RecyclerView.Adapter<CuratedAdapter.ViewHolder> {
    Context context;
    ArrayList<Photo> list;
    OnRecyclerClickListtner listtner;

    public CuratedAdapter(Context context, ArrayList<Photo> list, OnRecyclerClickListtner listtner) {
        this.context = context;
        this.list = list;
        this.listtner = listtner;
    }

    @NonNull
    @Override
    public CuratedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_recycler_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CuratedAdapter.ViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getSrc().large).placeholder(R.drawable.wallpaper_svgrepo_com).into(holder.binding.imageviewList);
        holder.binding.homeRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listtner.onClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HomeRecyclerRowBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=HomeRecyclerRowBinding.bind(itemView);

        }
    }
}

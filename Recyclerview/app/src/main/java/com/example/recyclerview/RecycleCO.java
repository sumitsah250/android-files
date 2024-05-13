package com.example.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleCO extends RecyclerView.Adapter<RecycleCO.ViewHolder>{
    Context context;
    ArrayList<ContactModel> arrContacts;
    RecycleCO(Context context,ArrayList<ContactModel> arrContacts){
        this.context=context;
        this.arrContacts=arrContacts;
    }
    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.img.setImageResource(arrContacts.get(position).img);
        holder.txtName.setText(arrContacts.get(position).name);
        holder.txtNumber.setText(arrContacts.get(position).number);
    }
    @Override
    public int getItemCount() {
        return arrContacts.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtNumber;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName=itemView.findViewById(R.id.txtname);
            txtNumber=itemView.findViewById(R.id.txtcontact);
            img=itemView.findViewById(R.id.imgcontact);
        }
    }
}

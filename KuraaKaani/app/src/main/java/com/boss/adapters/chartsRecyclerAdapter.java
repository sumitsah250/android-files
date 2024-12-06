package com.boss.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.kuraakaani.R;
import com.boss.kuraakaani.chatActivity;
import com.boss.kuraakaani.databinding.RowConversationBinding;
import com.boss.modelclass.myUsers;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class chartsRecyclerAdapter extends RecyclerView.Adapter<chartsRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<myUsers> users;

    public chartsRecyclerAdapter(Context context, ArrayList<myUsers> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public chartsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_conversation,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chartsRecyclerAdapter.ViewHolder holder, int position) {
        myUsers user = users.get(position);

        String SenderId = FirebaseAuth.getInstance().getUid();
        String SenderRoom =SenderId+user.getUid();

        FirebaseDatabase.getInstance().getReference().child("chats")
                        .child(SenderRoom)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()) {
                                            String LastMsg = snapshot.child("lastMessage").getValue(String.class);
                                            long lasttime = snapshot.child("lastMessageTime").getValue(Long.class);
                                            holder.binding.lastmessage.setText(LastMsg);
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
                                            holder.binding.messagetime.setText(simpleDateFormat.format(lasttime));
                                            holder.binding.messagetime.setVisibility(View.VISIBLE);
                                        }else{
                                            holder.binding.lastmessage.setText("Tap to chart");
                                            holder.binding.messagetime.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
        holder.binding.username.setText(user.getUsername());
        Glide.with(context).load(user.getProfileImage())
                .placeholder(R.drawable.person_circle_svgrepo_com)
                .into(holder.binding.profile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,chatActivity.class);
                intent.putExtra("name",user.getUsername());
                intent.putExtra("uid",user.getUid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowConversationBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=RowConversationBinding.bind(itemView);
        }
    }
}

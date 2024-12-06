package com.boss.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.kuraakaani.MainActivity;
import com.boss.kuraakaani.R;
import com.boss.kuraakaani.databinding.ItemSendBinding;
import com.boss.kuraakaani.databinding.ItemStatusBinding;
import com.boss.modelclass.Status;
import com.boss.modelclass.UserStatus;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;

public class TopStatusAdapter extends RecyclerView.Adapter<TopStatusAdapter.TopStatusViewHolder> {
    Context context;
    ArrayList<UserStatus> userStatuses;

    public TopStatusAdapter(Context context, ArrayList<UserStatus> userStatuses) {
        this.context = context;
        this.userStatuses = userStatuses;
    }

    @NonNull
    @Override
    public TopStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status,parent,false);
        return new TopStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopStatusViewHolder holder, int position) {
        UserStatus userStatus = userStatuses.get(position);
        holder.binding.txtstatusname.setText(userStatus.getName());
        if(userStatus.getStatuses().size()-1>=0){
            Status lastStatus = userStatus.getStatuses().get(userStatus.getStatuses().size()-1);
            try{
                Glide.with(context).load(lastStatus.getImageUrl()).into(holder.binding.image);
                holder.binding.circularStatusView.setPortionsCount(userStatus.getStatuses().size());
            }catch (Exception e){
                Log.d("status Adapter1",""+e);
            }

        }


        holder.binding.circularStatusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MyStory> myStories = new ArrayList<>();
                for(Status status : userStatus.getStatuses()){
                    myStories.add(new MyStory(status.getImageUrl()));
                }
                try{
                    new StoryView.Builder(((MainActivity)context).getSupportFragmentManager())
                            .setStoriesList(myStories) // Required
                            .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
                            .setTitleText(userStatus.getName()) // Default is Hidden
                            .setSubtitleText("") // Default is Hidden
                            .setTitleLogoUrl(userStatus.getProfileImage()) // Default is Hidden
                            .setStoryClickListeners(new StoryClickListeners() {
                                @Override
                                public void onDescriptionClickListener(int position) {
                                    //your action
                                }

                                @Override
                                public void onTitleIconClickListener(int position) {
                                    //your action
                                }
                            }) // Optional Listeners
                            .build() // Must be called before calling show method
                            .show();


                }catch (Exception e){
                    Log.d("status Adapter",""+e);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return userStatuses.size();
    }

    public class TopStatusViewHolder extends RecyclerView.ViewHolder{
        ItemStatusBinding binding;

        public TopStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemStatusBinding.bind(itemView);
        }
    }
}

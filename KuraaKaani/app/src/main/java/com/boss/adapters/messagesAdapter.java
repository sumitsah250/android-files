package com.boss.adapters;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.kuraakaani.R;
import com.boss.kuraakaani.databinding.ItemRecivedBinding;
import com.boss.kuraakaani.databinding.ItemSendBinding;
import com.boss.modelclass.myMessage;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class messagesAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<myMessage> mymessages;
    final int ITEM_SENT=1;
    final int ITEM_RECEIVE=2;
    private String SenderRoom;
    private String ReciverRoom;

    public messagesAdapter(Context context, ArrayList<myMessage> mymessages, String senderRoom, String reciverRoom) {
        this.context = context;
        this.mymessages = mymessages;
        SenderRoom = senderRoom;
        ReciverRoom = reciverRoom;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SENT){
            View view = LayoutInflater.from(context).inflate(R.layout.item_send,parent,false);
            return new SentViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_recived,parent,false);
            return new ReceiveViewHoolder(view);

        }

    }

    @Override
    public int getItemViewType(int position) {
        myMessage message = mymessages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(message.getSenderId())){
            return ITEM_SENT;
        }else{
            return ITEM_RECEIVE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        myMessage mymessage = mymessages.get(position);
        int[] reactions =new int[]{
                R.drawable.no_svgrepo_com,
                R.drawable.ic_fb_like,
                R.drawable.ic_fb_love,
                R.drawable.ic_fb_laugh,
                R.drawable.ic_fb_wow,
                R.drawable.ic_fb_sad,
                R.drawable.ic_fb_angry
        };
        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(reactions)
                .build();

        ReactionPopup popup = new ReactionPopup(context, config, (pos) -> {

            if(pos>0){
                if(holder.getClass()==SentViewHolder.class) {
                    SentViewHolder viewHolder = (SentViewHolder) holder;
                    viewHolder.binding.feelings.setImageResource(reactions[pos]);
                    viewHolder.binding.feelings.setVisibility(View.VISIBLE);
                }else {
                    ReceiveViewHoolder viewHolder = (ReceiveViewHoolder) holder;
                    viewHolder.binding.feelings.setImageResource(reactions[pos]);
                    viewHolder.binding.feelings.setVisibility(View.VISIBLE);

                }
                mymessage.setFeeling(pos);
                FirebaseDatabase.getInstance().getReference()
                        .child("chats")
                        .child(SenderRoom).child("messages")
                        .child(mymessage.getMessageId()).setValue(mymessage);

                FirebaseDatabase.getInstance().getReference()
                        .child("chats")
                        .child(ReciverRoom).child("messages")
                        .child(mymessage.getMessageId()).setValue(mymessage);
            }else{
                if(holder.getClass()==SentViewHolder.class) {
                    SentViewHolder viewHolder = (SentViewHolder) holder;
                    viewHolder.binding.feelings.setImageResource(reactions[pos]);
                    viewHolder.binding.feelings.setVisibility(View.GONE);
                }else {
                    ReceiveViewHoolder viewHolder = (ReceiveViewHoolder) holder;
                    viewHolder.binding.feelings.setImageResource(reactions[pos]);
                    viewHolder.binding.feelings.setVisibility(View.GONE);

                }
                mymessage.setFeeling(pos);
                FirebaseDatabase.getInstance().getReference()
                        .child("chats")
                        .child(SenderRoom).child("messages")
                        .child(mymessage.getMessageId()).setValue(mymessage);

                FirebaseDatabase.getInstance().getReference()
                        .child("chats")
                        .child(ReciverRoom).child("messages")
                        .child(mymessage.getMessageId()).setValue(mymessage);

            }
            return true; // true is closing popup, false is requesting a new selection

        });

        if(holder.getClass()==SentViewHolder.class){
            SentViewHolder viewHolder = (SentViewHolder) holder;
            viewHolder.binding.message.setText(mymessage.getMessage());
            if(mymessage.getFeeling()>0){
//                mymessage.setFeeling(reactions[mymessage.getFeeling()]);
                viewHolder.binding.feelings.setImageResource(reactions[mymessage.getFeeling()]);
                viewHolder.binding.feelings.setVisibility(View.VISIBLE);
            }else{
                viewHolder.binding.feelings.setVisibility(View.GONE);
            }




            viewHolder.binding.message.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v,event);
                    return false;
                }
            });

        }else{
            ReceiveViewHoolder viewHolder = (ReceiveViewHoolder) holder;
            viewHolder.binding.message.setText(mymessage.getMessage());
            if(mymessage.getFeeling()>0){
                //                mymessage.setFeeling(reactions[mymessage.getFeeling()]);
                viewHolder.binding.feelings.setImageResource(reactions[mymessage.getFeeling()]);
                viewHolder.binding.feelings.setVisibility(View.VISIBLE);
            }else{
                viewHolder.binding.feelings.setVisibility(View.GONE);
            }
            viewHolder.binding.message.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v,event);
                    return false;
                }
            });

        }





    }

    @Override
    public int getItemCount() {
        return mymessages.size();
    }

    public class  SentViewHolder extends RecyclerView.ViewHolder {
        ItemSendBinding binding;
        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemSendBinding.bind(itemView);
        }
    }
    public class  ReceiveViewHoolder extends RecyclerView.ViewHolder {
        ItemRecivedBinding binding;
        public ReceiveViewHoolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemRecivedBinding.bind(itemView);
        }
    }
}

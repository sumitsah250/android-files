package com.boss.kuraakaani;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.boss.adapters.messagesAdapter;
import com.boss.kuraakaani.databinding.ActivityChatBinding;
import com.boss.modelclass.myMessage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class chatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    messagesAdapter MessagesAdapter;
    ArrayList<myMessage> messages;
    String SenderRoom,receiverRoom;
    FirebaseDatabase firebaseDatabase;

    //for video call
//    URL serverURL;
    // for video call

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding= ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        firebaseDatabase=FirebaseDatabase.getInstance();
        messages=new ArrayList<>();


        String username = getIntent().getStringExtra("name");
        String ReceiverUid= getIntent().getStringExtra("uid");
        String SenderUid = FirebaseAuth.getInstance().getUid();

        SenderRoom =SenderUid+ReceiverUid;
        receiverRoom=ReceiverUid+SenderUid;


        MessagesAdapter =new messagesAdapter(this,messages,SenderRoom,receiverRoom);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(MessagesAdapter);

        firebaseDatabase.getReference().child("chats")
                        .child(SenderRoom)
                                .child("messages")
                                        .addValueEventListener(new ValueEventListener() {
                                            @SuppressLint("NotifyDataSetChanged")
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                messages.clear();
                                                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                                    myMessage message = snapshot1.getValue(myMessage.class);
                                                    message.setMessageId(snapshot1.getKey());
                                                    messages.add(message);
                                                }
                                                MessagesAdapter.notifyDataSetChanged();


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
        binding.sendbuttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText=binding.edtmessage.getText().toString();
                Date date =new Date();

                myMessage message = new myMessage(messageText,SenderUid,date.getTime());
                binding.edtmessage.setText("");

                String randomKey= firebaseDatabase.getReference().push().getKey();
                HashMap<String,Object> lastMsgObj = new HashMap<>();
                lastMsgObj.put("lastMessage",message.getMessage());
                lastMsgObj.put("lastMessageTime",date.getTime());
                firebaseDatabase.getReference().child("chats").child(SenderRoom).updateChildren(lastMsgObj);
                firebaseDatabase.getReference().child("chats").child(receiverRoom).updateChildren(lastMsgObj);

                firebaseDatabase.getReference().child("chats")
                        .child(SenderRoom)
                        .child("messages")
                        .child(Objects.requireNonNull(randomKey))
                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                firebaseDatabase.getReference().child("chats")
                                        .child(receiverRoom)
                                        .child("messages")
                                        .child(randomKey)
                                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                                Log.d("chatActivity","Success");

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("chatActivity","failed");
                            }
                        });




            }
        });







        setSupportActionBar(binding.toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Kuraakaani");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setTitle(username);

        //video call
//        try {
//            serverURL=new URL("https://meet.jit.si");
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        // Somewhere early in your app.
//        JitsiMeetConferenceOptions defaultOptions
//                = new JitsiMeetConferenceOptions.Builder()
//                .setServerURL(serverURL)
//                // When using JaaS, set the obtained JWT here
//                //.setToken("MyJWT")
//                // Different features flags can be set
//                // .setFeatureFlag("toolbox.enabled", false)
//                // .setFeatureFlag("filmstrip.enabled", false)
//                .setFeatureFlag("welcomepage.enabled", true)
//                .build();
//        JitsiMeet.setDefaultConferenceOptions(defaultOptions);


        //video call




    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.videomenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.videochat){
            //video call
//            JitsiMeetConferenceOptions options
//                    = new JitsiMeetConferenceOptions.Builder()
//                    .setRoom("sumit")
//                    // Settings for audio and video
//                    //.setAudioMuted(true)
//                    //.setVideoMuted(true)
//                    .setFeatureFlag("welcomepage.enabled", true)
//
//                    .build();
//// Launch the new activity with the given options. The launch() method takes care
//// of creating the required Intent and passing the options.
//            JitsiMeetActivity.launch(this, options);

            //video call



        }
        return super.onOptionsItemSelected(item);
    }
}
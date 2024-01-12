package com.example.customnotification;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnDial,btnEmail,btnMsg,btnShare;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDial= findViewById(R.id.btnDial);
        btnShare=findViewById(R.id.btnShare);
        btnEmail= findViewById(R.id.btnEmail);
        btnMsg = findViewById(R.id.btnMsg);

        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDial = new Intent(Intent.ACTION_DIAL);
                iDial.setData(Uri.parse("tel: +9779809641235"));
                startActivity(iDial);
            }
        });
        btnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMsg= new Intent(Intent.ACTION_SENDTO);
                iMsg.setData(Uri.parse("smsto:"+Uri.encode("+9779809641235")));
                iMsg.putExtra("sms_body","please this issue asap");
                startActivity(iMsg);
            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iEmail = new Intent(Intent.ACTION_SEND);
                iEmail.setType("massage/rfc822");
                iEmail.putExtra(Intent.EXTRA_EMAIL,new String[]{"sumitsah250@gmail.com"});
                iEmail.putExtra(Intent.EXTRA_SUBJECT,"queries");
                iEmail.putExtra(Intent.EXTRA_TEXT,"please perform the taks as soon ad possible");
                startActivity(Intent.createChooser(iEmail,"Email via"));

            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iShare = new Intent (Intent.ACTION_SEND);
                iShare.setType("text/plain");
                iShare.putExtra(Intent.EXTRA_TEXT,"i love this song checkout via this link,https://www.youtube.com/watch?v=WXBHCQYxwr0&list=RDWXBHCQYxwr0&start_radio=1");
                 startActivity(Intent.createChooser(iShare,"share via"));
            }
        });



    }
}
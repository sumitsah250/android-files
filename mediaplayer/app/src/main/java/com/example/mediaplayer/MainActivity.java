package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
    Button btnpaly,btnpause,btnstop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnpaly =findViewById(R.id.btnplay);
        btnpause =findViewById(R.id.btnpause);
        btnstop =findViewById(R.id.btnstop);

        MediaPlayer mp = new MediaPlayer().create(this,R.raw.mu);


//        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        String apath = "android.resource://"+getPackageCodePath()+"/raw/mu";
//        Uri audiouri = Uri.parse(apath);
//        try {
//            mp.setDataSource(this,audiouri);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            mp.prepare();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        btnpaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();

            }
        });
        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
                mp.seekTo(0);

            }
        });

    }
}
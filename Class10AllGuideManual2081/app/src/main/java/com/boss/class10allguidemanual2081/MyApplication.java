package com.boss.class10allguidemanual2081;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MemoryCacheSettings;
import com.google.firebase.firestore.PersistentCacheSettings;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Configure Firestore settings
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                // Use memory-only cache
                .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build())
                // Use persistent disk cache (default)
                .setLocalCacheSettings(PersistentCacheSettings.newBuilder().build())
                .build();

        // Apply the settings
        db.setFirestoreSettings(settings);
    }
}

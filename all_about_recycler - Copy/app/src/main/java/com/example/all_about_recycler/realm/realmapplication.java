package com.example.all_about_recycler.realm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class realmapplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}

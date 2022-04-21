package com.example.e_commerce.model;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Ruslan Kishai on 10/24/2017.
 * Copyright (C) 2017 EasyCount.
 */

public class EasyApplication extends Application {

    private static EasyApplication instance;

    public synchronized static EasyApplication getInstance() {
        if (instance == null)
            instance = new EasyApplication();
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;


            Realm.init(instance);

            final RealmConfiguration configuration = new RealmConfiguration.Builder().schemaVersion(1).migration(new RealmMigrations()).allowWritesOnUiThread(true).build();
            Realm.setDefaultConfiguration(configuration);
            Realm.getInstance(configuration);


    }
}

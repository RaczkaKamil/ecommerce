package com.example.e_commerce.model;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by ruslan.kishai on 3/22/18.
 */

public class RealmMigrations implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();

        System.out.println("OLD VERSION: " + oldVersion);
        System.out.println("NEW VERSION: " + newVersion);



    }


    }

package com.example.e_commerce.model;

import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class RealmManager {

    private static Realm realm;

    public static Realm open() {

        realm = Realm.getDefaultInstance();
        return realm;
    }

    public static void close() {
    }

    public static OrderDao createOrderDao() {
        checkForOpenRealm();
        return new OrderDao(realm);
    }


    public static void clear() {
        checkForOpenRealm();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.delete(Order.class);
            }
        });
    }

    private static void checkForOpenRealm() {
        if (realm == null || realm.isClosed())
            throw new IllegalStateException("RealmManager: Realm is closed, call open() method first");
    }
}

package com.example.e_commerce.model;

import androidx.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class OrderDao {
    private Realm realm;
    public OrderDao(@NonNull Realm realm) {
        this.realm = realm;
    }

    public RealmResults<Order> getAllProducts() {
        return realm
                .where(Order.class)
                .findAll();
    }

    public void addNewOrder( final Order newItem) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    newItem.setId(getLastProductId() + 1);
                    realm.copyToRealm(newItem);
                }
            });
    }

    public void addNewPhotoToOrder( String path, int id) {
        System.out.println("zapisuje do zamowienia o id: " + id + " zdjęcie o pathu: " + path);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Order order = getOrderByID(id);
                if(order != null){
                    order.setPhoto(order.getPhoto() +"," + path);
                    String[] paths = order.getPhoto().split(",");
                    int i = 0;
                    for (String p : paths) {
                        if ((p.length() > 5)) {
                           i++;
                        }
                    }

                        order.setPhotoCount(String.valueOf(i));

                }
            }
        });

    }

    public int getLastProductId() {
        Number max = realm.where(Order.class).max("id");
        return max == null ? 0 : max.intValue();
    }

    public Order getOrderByID(int id){
        return realm
                .where(Order.class)
                .equalTo("id", id)
                .findFirst();
    }
}

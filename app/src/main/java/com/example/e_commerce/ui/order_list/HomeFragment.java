package com.example.e_commerce.ui.order_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.model.Order;
import com.example.e_commerce.model.OrderDao;
import com.example.e_commerce.model.RealmManager;

import java.util.ArrayList;

import io.realm.RealmResults;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    HomeListAdapter adapter;
    ArrayList<Order> list= new ArrayList<>();
    OrderDao dao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.rv);
        RealmManager.open();
        //addOrder();
        //addOrder();
        refreshList();
        return root;
    }

    public void refreshList(){
        dao = RealmManager.createOrderDao();
        RealmResults<Order> realmResults =  dao.getAllProducts();
        list.clear();
        for (Order order_from_list : realmResults) { list.add(order_from_list); }
        adapter = new HomeListAdapter(list, getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    public void addOrder(){
        dao = RealmManager.createOrderDao();
        Order order = new Order();
        order.setTitle("Zamówienie 1");
        order.setName("Jan Kochanowski");
        order.setDate("2020-20-12");
        order.setPhotoCount("Brak zdjęć");
        dao.addNewOrder(order);
        refreshList();
    }

    @Override
    public void onResume() {
        System.out.println("on resume");
        super.onResume();
        refreshList();
    }
}
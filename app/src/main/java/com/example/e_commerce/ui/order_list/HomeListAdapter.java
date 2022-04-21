package com.example.e_commerce.ui.order_list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.ui.order_select.Main3Activity;
import com.example.e_commerce.R;
import com.example.e_commerce.model.Order;

import java.util.ArrayList;


public class HomeListAdapter  extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {
    ArrayList<Order> ordersList;
    Context context;
    HomeListAdapter(  ArrayList<Order> orderList, Context context){
        super();
        this.ordersList = orderList;
        this.context = context;
    }

    private Context getContext(){
        return this.context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.node_home, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = ordersList.get(position);
        System.out.println(order.toString());
    holder.tf_title.setText(order.getTitle());
    holder.tf_name.setText(order.getName());
    holder.tf_date.setText(order.getDate());
    holder.tf_photo.setText(order.getPhotoCount());
    holder.layout_holder.setOnClickListener(v -> {
        System.out.println("Wybrano " + order.getId());
        Intent intent = new Intent(getContext(), Main3Activity.class);
        intent.putExtra("ID", order.getId());
        getContext().startActivity(intent);
    });
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tf_title, tf_name,tf_date,tf_photo;
        ConstraintLayout layout_holder;
        ViewHolder(View itemView) {
            super(itemView);

            layout_holder = itemView.findViewById(R.id.layout_holder);
            tf_title = itemView.findViewById(R.id.tf_title);
            tf_name = itemView.findViewById(R.id.tf_name);
            tf_date = itemView.findViewById(R.id.tf_date);
            tf_photo = itemView.findViewById(R.id.tf_photo);



        }
    }

}

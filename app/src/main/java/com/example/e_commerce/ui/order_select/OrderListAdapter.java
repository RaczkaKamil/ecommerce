package com.example.e_commerce.ui.order_select;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.ui.order_select.Main3Activity;
import com.example.e_commerce.R;
import com.example.e_commerce.model.Order;

import java.io.File;
import java.util.ArrayList;


public class OrderListAdapter  extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    ArrayList<String> ordersList;
    Context context;
    OrderListAdapter(ArrayList<String> orderList){
        super();
        this.ordersList = orderList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.node_photo, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String photoPath = ordersList.get(position);
        System.out.println(photoPath);
        String[] photo = photoPath.split("\\*");
        System.out.println(photo[0]);
        System.out.println(photo[1]);

        Bitmap myBitmap = BitmapFactory.decodeFile(photo[1]);

        holder.imageView.setImageBitmap(myBitmap);
        holder.tf_photo_date.setText(photo[0]);
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tf_photo_date;
        ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tf_photo_date = itemView.findViewById(R.id.tf_photo_date);
        }
    }

}

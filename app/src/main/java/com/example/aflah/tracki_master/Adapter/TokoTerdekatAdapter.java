package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.aflah.tracki_master.DetailToko.DetailTokoActivity;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TokoTerdekatAdapter extends RecyclerView.Adapter<TokoTerdekatAdapter.MyViewHolder> {

    private Context context;
    private HashMap<String, Store> stores;

    List<Store> storeList;

    public TokoTerdekatAdapter(Context context, HashMap<String, Store> stores) {
        this.context = context;
        this.stores = stores;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_toko_terdekat, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        storeList = new ArrayList<>(stores.values());
        holder.tv_namaToko_tokoTerdekat.setText(storeList.get(position).getName());

        holder.cardView_tokoTerdkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailTokoActivity.class);
                intent.putExtra("idTokoTerdekat", storeList.get(position).getId());
                context.startActivity(intent);
            }
        });

        holder.ratingBar_tokoTerdekat.setRating((float)storeList.get(position).getRating());

        Picasso.get().load(storeList.get(position).getLogo()).into(holder.img_tokoTerdekat);
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img_tokoTerdekat;
        TextView tv_namaToko_tokoTerdekat;
        CardView cardView_tokoTerdkat;
        RatingBar ratingBar_tokoTerdekat;

        public MyViewHolder(View itemView) {
            super(itemView);

            img_tokoTerdekat = (ImageView) itemView.findViewById(R.id.iv_toko_tokoTerdekat);
            tv_namaToko_tokoTerdekat = (TextView) itemView.findViewById(R.id.tv_namaToko_tokoTerdekat);
            ratingBar_tokoTerdekat = (RatingBar) itemView.findViewById(R.id.rating_itemTokoTerdekat);
            cardView_tokoTerdkat = (CardView) itemView.findViewById(R.id.cardview_toko_tokoTerdekat);
        }
    }
}

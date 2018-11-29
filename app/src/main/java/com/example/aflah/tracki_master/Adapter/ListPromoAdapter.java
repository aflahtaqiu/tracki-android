package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.DetailPromoActivity;
import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListPromoAdapter extends RecyclerView.Adapter<ListPromoAdapter.MyViewHolder> {

    private Context context;
    private HashMap<String, Store> stores;
    List<Store> storeList;
    List<Promotion> promotions;
    List<Store> storeListTemp;

    public ListPromoAdapter(Context context, HashMap<String, Store> stores) {
        this.context = context;
        this.stores = stores;
        storeList = new ArrayList<>(stores.values());
        promotions = new ArrayList<>();
        for (Store store : storeList){
            if (!(store.getPromotions().isEmpty() || store.getPromotions() == null)){
                for (Promotion promotion : store.getPromotions()){
                    promotion.setStore(store);
                    promotions.add(promotion);
                }
            }
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_promo, parent, false);
        ListPromoAdapter.MyViewHolder myViewHolder = new ListPromoAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.namaPromo.setText(promotions.get(position).getTitle());
        holder.namaToko.setText(promotions.get(position).getStore().getName());

        Picasso.get().load(promotions.get(position).getBanner()).into(holder.gambarPromo);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailPromoActivity.class);
                intent.putExtra("idPromo", promotions.get(position).getId());
                intent.putExtra("idToko", promotions.get(position).getStore().getId());
                intent.putExtra("namaToko", promotions.get(position).getStore().getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView namaToko, namaPromo;
        ImageView gambarPromo;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            namaToko = itemView.findViewById(R.id.namaToko_itemPromo);
            namaPromo = itemView.findViewById(R.id.namaPromo_itemPromo);
            gambarPromo = itemView.findViewById(R.id.gambarPromo_itemPromo);
            cardView = itemView.findViewById(R.id.cardview_promo);
        }
    }
}

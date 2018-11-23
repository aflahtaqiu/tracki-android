package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListPromoAdapter extends RecyclerView.Adapter<ListPromoAdapter.MyViewHolder> {

    private Context context;
    private HashMap<String, Store> stores;
    List<Store> storeList;
    List<Promotion> promotions;

    public ListPromoAdapter(Context context, HashMap<String, Store> stores) {
        this.context = context;
        this.stores = stores;
        storeList = new ArrayList<>(stores.values());
        promotions = new ArrayList<>();
        for (Store store : storeList){
            if (!(store.getPromotions().isEmpty() || store.getPromotions() == null)){
                for (Promotion promotion : store.getPromotions()){
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
        holder.textViewIsi.setText(promotions.get(position).getTitle() + ", "+ promotions.get(position).getDescription() + " id toko: " +
        promotions.get(position).getPivot().getStore_id());
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewIsi;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewIsi = itemView.findViewById(R.id.isiCardPromo);
        }
    }
}

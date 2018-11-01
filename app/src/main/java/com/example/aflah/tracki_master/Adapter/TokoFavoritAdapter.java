package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import java.security.PrivilegedAction;
import java.util.List;

/**
 * Created by ASUS on 10/31/2018.
 */

public class TokoFavoritAdapter extends RecyclerView.Adapter<TokoFavoritAdapter.MyViewHolder> {

    private Context context;
    private List<Store> favouriteStores;

    public TokoFavoritAdapter(Context context, List<Store> favouriteStores) {
        this.context = context;
        this.favouriteStores = favouriteStores;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_toko_favorit, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.namaToko.setText(favouriteStores.get(position).getName());
        Picasso.get().load(favouriteStores.get(position).getLogo()).fit().into(holder.iv_tokoFavorite);
    }

    @Override
    public int getItemCount() {
        return favouriteStores.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        CardView cardView;
        ImageView iv_tokoFavorite;
        TextView namaToko;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardview_toko_favorit);
            iv_tokoFavorite = (ImageView) itemView.findViewById(R.id.iv_toko_favorit);
            namaToko = (TextView) itemView.findViewById(R.id.tv_namaToko_favorit);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_favorit);
        }
    }

}

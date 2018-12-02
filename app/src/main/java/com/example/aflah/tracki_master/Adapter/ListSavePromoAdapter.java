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
import android.widget.TextView;

import com.example.aflah.tracki_master.DetailPromoActivity;
import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListSavePromoAdapter extends RecyclerView.Adapter<ListSavePromoAdapter.MyViewHolder> {

    private Context context;
    private List<Promotion> promotions;

    public ListSavePromoAdapter(Context context, List<Promotion> promotions) {
        this.context = context;
        this.promotions = promotions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_save_promo, parent, false);
        ListSavePromoAdapter.MyViewHolder myViewHolder = new ListSavePromoAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.namaPromo.setText(promotions.get(position).getTitle());
        holder.namaToko.setText(promotions.get(position).getStores().get(0).getName());

        Picasso.get().load(promotions.get(position).getBanner()).into(holder.gambarPromo);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailPromoActivity.class);
                intent.putExtra("idPromo", promotions.get(position).getId());
                intent.putExtra("namaToko", promotions.get(position).getStores().get(0).getName());
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
            cardView = itemView.findViewById(R.id.cardview_save_promo);
        }
    }
}

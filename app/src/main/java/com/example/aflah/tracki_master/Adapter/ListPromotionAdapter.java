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

import com.example.aflah.tracki_master.View.DetailPromoActivity;
import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListPromotionAdapter extends RecyclerView.Adapter<ListPromotionAdapter.MyViewHolder> {

    private Context context;
    private List<Promotion> promotionList;

    public ListPromotionAdapter(Context context, List<Promotion> promotionList) {
        this.context = context;
        this.promotionList = promotionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_promo, parent, false);
        ListPromotionAdapter.MyViewHolder myViewHolder = new ListPromotionAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.namaPromo.setText(promotionList.get(position).getTitle());
        holder.namaToko.setText(promotionList.get(position).getStores().get(0).getName());
        holder.tanggalPromo.setText(promotionList.get(position).getExpired_date());

        try {
            String expireDateStr;
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateExpire = inputFormat.parse(promotionList.get(position).getExpired_date());
            expireDateStr = outputFormat.format(dateExpire);
            holder.tanggalPromo.setText(expireDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picasso.get().load(promotionList.get(position).getBanner()).into(holder.gambarPromo);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailPromoActivity.class);
                intent.putExtra("idPromo", promotionList.get(position).getId());
                intent.putExtra("idToko", promotionList.get(position).getStores().get(0).getId());
                intent.putExtra("namaToko", promotionList.get(position).getStores().get(0).getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return promotionList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView namaToko,namaPromo, tanggalPromo;
        ImageView gambarPromo;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            namaToko = itemView.findViewById(R.id.namaToko_itemPromo);
            namaPromo = itemView.findViewById(R.id.namaPromo_itemPromo);
            gambarPromo = itemView.findViewById(R.id.gambarPromo_itemPromo);
            tanggalPromo = itemView.findViewById(R.id.tanggalPromo_itemPromo);
            cardView = itemView.findViewById(R.id.cardview_promo);
        }
    }
}

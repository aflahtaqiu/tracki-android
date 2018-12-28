package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.aflah.tracki_master.Auth.LoginActivity;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.ReviewToko.ReviewTokoActivity;
import com.squareup.picasso.Picasso;


import de.hdodenhof.circleimageview.CircleImageView;

public class DetailTokoAdapter extends RecyclerView.Adapter<DetailTokoAdapter.MyViewHolder> {

    private Context context;
    private Store store;
    String userToken;

    public DetailTokoAdapter(Context context, Store store, String userToken) {
        this.context = context;
        this.store = store;
        this.userToken = userToken;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_toko, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.namaToko.setText(store.getName());
        holder.tipeToko.setText(store.getStore_type().getName());
        holder.lokasiToko.setText(store.getLocation());
        holder.jamBukaToko.setText(store.getOpen());
        holder.jamTutupToko.setText(store.getClose());
        holder.noTelpToko.setText(store.getPhone());
        holder.ratingBarToko.setRating((float) store.getRating());

        holder.btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userToken.isEmpty()){
                    context.startActivity(new Intent(v.getContext(), LoginActivity.class));
                }else {
                    Intent intent = new Intent(v.getContext(), ReviewTokoActivity.class);
                    intent.putExtra("idToko", store.getId());
                    context.startActivity(intent);
                }
            }
        });

        holder.noTelpToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+"+store.getPhone()));
                context.startActivity(intent);
            }
        });

        Picasso.get().load(store.getLogo()).into(holder.img_toko_detailToko);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namaToko, tipeToko, lokasiToko, jamBukaToko, jamTutupToko, noTelpToko;
        CircleImageView img_toko_detailToko;
        Button btnReview;
        RatingBar ratingBarToko;

        public MyViewHolder(View itemView) {
            super(itemView);

            namaToko = (TextView) itemView.findViewById(R.id.tv_namaToko_detailToko);
            tipeToko = (TextView) itemView.findViewById(R.id.tv_tipeToko_detailToko);
            lokasiToko = (TextView) itemView.findViewById(R.id.tv_lokasiToko_detailToko);
            jamBukaToko = (TextView) itemView.findViewById(R.id.tv_jamBukaToko_detailToko);
            jamTutupToko = (TextView) itemView.findViewById(R.id.tv_jamTutupToko_detailToko);
            noTelpToko = (TextView) itemView.findViewById(R.id.tv_phoneToko_detailToko);
            img_toko_detailToko = (CircleImageView) itemView.findViewById(R.id.iv_logo_detail_toko);
            btnReview = (Button) itemView.findViewById(R.id.btn_reviewToko_detailToko);
            ratingBarToko = (RatingBar) itemView.findViewById(R.id.ratingToko_detailToko);
        }
    }
}

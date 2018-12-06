package com.example.aflah.tracki_master.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aflah.tracki_master.DetailPromoActivity;
import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.Response.ResponseDeletePromo;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.NavigationActivity;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.google.android.gms.common.api.Api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSavePromoAdapter extends RecyclerView.Adapter<ListSavePromoAdapter.MyViewHolder> {

    private Context context;
    private List<Promotion> promotions;
    String userToken;
    RecyclerView recyclerView;
    TextView tvNoPromo;

    public ListSavePromoAdapter(Context context, List<Promotion> promotions, String userToken, RecyclerView recyclerView, TextView tvNoPromo) {
        this.context = context;
        this.promotions = promotions;
        this.userToken = userToken;
        this.recyclerView = recyclerView;
        this.tvNoPromo = tvNoPromo;
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

        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                Call<ResponseDeletePromo> deletePromoCall = apiRequest.deletePromo(userToken, promotions.get(position).getId());
                deletePromoCall.enqueue(new Callback<ResponseDeletePromo>() {
                    @Override
                    public void onResponse(Call<ResponseDeletePromo> call, Response<ResponseDeletePromo> response) {

                        ApiRequest apiRequest1 = RetroServer.getClient().create(ApiRequest.class);
                        Call<ResponseUserById> getSavedPromo = apiRequest.getSavedPromo(promotions.get(position).getPivot().getUser_id());
                        getSavedPromo.enqueue(new Callback<ResponseUserById>() {
                            @Override
                            public void onResponse(Call<ResponseUserById> call, Response<ResponseUserById> response) {
                                List<Promotion> promotions = new ArrayList<>();
                                for (Promotion promotion : response.body().getUnused_promotions()){
                                    promotions.add(promotion);
                                }
                                if (promotions.size() != 0) tvNoPromo.setVisibility(View.INVISIBLE);
                                else tvNoPromo.setVisibility(View.VISIBLE);
                                ListSavePromoAdapter listSavePromoAdapter = new ListSavePromoAdapter(context, promotions, userToken, recyclerView, tvNoPromo);
                                recyclerView.setAdapter(listSavePromoAdapter);
                            }

                            @Override
                            public void onFailure(Call<ResponseUserById> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ResponseDeletePromo> call, Throwable t) {

                    }
                });
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
        ImageButton imageButtonDelete;

        public MyViewHolder(View itemView) {
            super(itemView);

            namaToko = itemView.findViewById(R.id.namaToko_itemPromo);
            namaPromo = itemView.findViewById(R.id.namaPromo_itemPromo);
            gambarPromo = itemView.findViewById(R.id.gambarPromo_itemPromo);
            cardView = itemView.findViewById(R.id.cardview_save_promo);
            imageButtonDelete = (ImageButton) itemView.findViewById(R.id.btn_deletePromo);
        }
    }
}

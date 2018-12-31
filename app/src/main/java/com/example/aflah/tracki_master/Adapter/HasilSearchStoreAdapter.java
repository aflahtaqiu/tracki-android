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

import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.View.TokoActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HasilSearchStoreAdapter extends RecyclerView.Adapter<HasilSearchStoreAdapter.MyViewHolder>{
    private Context context;
    private List<Store> listStore;

    public HasilSearchStoreAdapter(Context context, List listStore){
        this.context = context;
        this.listStore = listStore;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hasil_search, parent, false);
        HasilSearchStoreAdapter.MyViewHolder myViewHolder = new HasilSearchStoreAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nama.setText(listStore.get(position).getName());
        holder.lokasiOrharga.setText(listStore.get(position).getLocation());
        Picasso.get().load(listStore.get(position).getLogo()).into(holder.gambar);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),TokoActivity.class);
                intent.putExtra("idTokoTerdekat",Integer.valueOf(listStore.get(position).getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {return listStore.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama,lokasiOrharga;
        ImageView gambar;
        CardView cardView;

        public MyViewHolder(View itemView){
            super(itemView);

            nama = itemView.findViewById(R.id.tv_nama);
            lokasiOrharga = itemView.findViewById(R.id.tv_lokasi);
            gambar = itemView.findViewById(R.id.tv_gambar);
            cardView = itemView.findViewById(R.id.cardview_hasil_search);
        }
    }
}

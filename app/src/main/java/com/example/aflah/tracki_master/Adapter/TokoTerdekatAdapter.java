package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aflah.tracki_master.DetailTokoActivity;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TokoTerdekatAdapter extends RecyclerView.Adapter<TokoTerdekatAdapter.MyViewHolder> {

    private Context context;
    private List<Store> stores;

    public TokoTerdekatAdapter(Context context, List<Store> stores) {
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
        holder.tv_namaToko_tokoTerdekat.setText(stores.get(position).getName());

        holder.cardView_tokoTerdkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailTokoActivity.class);
                intent.putExtra("idTokoTerdekat", stores.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img_tokoTerdekat;
        TextView tv_namaToko_tokoTerdekat;
        CardView cardView_tokoTerdkat;

        public MyViewHolder(View itemView) {
            super(itemView);

            img_tokoTerdekat = (CircleImageView) itemView.findViewById(R.id.iv_toko_tokoTerdekat);
            tv_namaToko_tokoTerdekat = (TextView) itemView.findViewById(R.id.tv_namaToko_tokoTerdekat);
            cardView_tokoTerdkat = (CardView) itemView.findViewById(R.id.cardview_toko_tokoTerdekat);
        }
    }
}

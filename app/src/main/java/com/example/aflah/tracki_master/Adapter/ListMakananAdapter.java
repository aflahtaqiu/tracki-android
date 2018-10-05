package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListMakananAdapter extends RecyclerView.Adapter<ListMakananAdapter.MyViewHolder> {

    private Context context;
    private Store store;

    public ListMakananAdapter(Context context, Store store) {
        this.context = context;
        this.store = store;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_makanan, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (store.getProducts().get(position).getCategory().getId() == 1){

            holder.namaMakanan.setText(store.getProducts().get(position).getName());
            holder.hargaMakanan.setText(String.valueOf((int) store.getProducts().get(position).getPrice()));
        }
        else {
            holder.namaMakanan.setVisibility(View.GONE);
            holder.hargaMakanan.setVisibility(View.GONE);
            holder.rpMakanan.setVisibility(View.GONE);
            holder.img_makanan.setVisibility(View.GONE);
            holder.linearLayout_makanan_item.setPadding(10, 0, 10, 0);
        }
    }

    @Override
    public int getItemCount() {
        return store.getProducts().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namaMakanan, hargaMakanan, rpMakanan;
        CircleImageView img_makanan;
        LinearLayout linearLayout_makanan_item;

        public MyViewHolder(View itemView) {
            super(itemView);

            namaMakanan = (TextView) itemView.findViewById(R.id.tv_namaMakanan_makanan);
            hargaMakanan = (TextView) itemView.findViewById(R.id.tv_hargaMakanan_makanan);
            rpMakanan = (TextView) itemView.findViewById(R.id.tv_rpMakanan_makanan);
            img_makanan = (CircleImageView) itemView.findViewById(R.id.img_makanan_makanan);
            linearLayout_makanan_item = (LinearLayout) itemView.findViewById(R.id.linearlayout_item_makanan);
        }
    }
}

package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aflah.tracki_master.DetaiMenu.DetailMenuActivity;
import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListMinumanAdapter extends RecyclerView.Adapter<ListMinumanAdapter.MyViewHolder> {

    private Context context;
    private Store store;
    NumberFormat numberFormat = new DecimalFormat("#,###");

    public ListMinumanAdapter(Context context, Store store) {
        this.context = context;
        this.store = store;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_minuman, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (store.getProducts().get(position).getCategory().getId() == 2){
            holder.namaMinuman.setText(store.getProducts().get(position).getName());
            holder.hargaMinuaman.setText(numberFormat.format(store.getProducts().get(position).getPrice()).replace(',','.'));
            Picasso.get().load(store.getProducts().get(position).getPicture()).into(holder.img_minuman);
        }
        else {
            holder.img_minuman.setVisibility(View.GONE);
            holder.rpMinuman.setVisibility(View.GONE);
            holder.hargaMinuaman.setVisibility(View.GONE);
            holder.namaMinuman.setVisibility(View.GONE);
            holder.linearLayout_minuman_item.setPadding(10, 0, 10, 0 );
        }
    }

    @Override
    public int getItemCount() {

        return store.getProducts().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView namaMinuman, hargaMinuaman, rpMinuman;
        CircleImageView img_minuman;
        LinearLayout linearLayout_minuman_item;

        public MyViewHolder(View itemView) {
            super(itemView);

            namaMinuman = (TextView) itemView.findViewById(R.id.tv_namaMimunam_minuman);
            hargaMinuaman = (TextView) itemView.findViewById(R.id.tv_hargaMinuman_minuman);
            rpMinuman = (TextView) itemView.findViewById(R.id.tv_rpminuman_minuman);
            img_minuman = (CircleImageView) itemView.findViewById(R.id.img_minuman_minuman);
            linearLayout_minuman_item = (LinearLayout) itemView.findViewById(R.id.linearlayout_item_minuman);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int posisi = getAdapterPosition();
                    if (posisi != RecyclerView.NO_POSITION){
                        Product clickedData = store.getProducts().get(posisi);
                        Intent intent = new Intent(v.getContext(), DetailMenuActivity.class);
                        intent.putExtra("idProduk", clickedData.getId());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}

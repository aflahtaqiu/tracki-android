package com.example.aflah.tracki_master.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.DetailMenuActivity;
import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListMakananAdapter extends RecyclerView.Adapter<ListMakananAdapter.MyViewHolder> {

    private Context context;
    private Store store;
    NumberFormat numberFormat = new DecimalFormat("#,###");

    public ListMakananAdapter(Context context, Store store) {
        this.context = context;
        this.store = store;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_makanan, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (store.getProducts().get(position).getCategory().getId() == 1){

            holder.namaMakanan.setText(store.getProducts().get(position).getName());
            holder.hargaMakanan.setText(numberFormat.format(store.getProducts().get(position).getPrice()).replace(',','.'));
            Log.v("numberFormat", numberFormat.format(store.getProducts().get(position).getPrice()));
            Picasso.get().load(store.getProducts().get(position).getPicture()).into(holder.img_makanan);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posisi = getAdapterPosition();
                    if (posisi != RecyclerView.NO_POSITION){
                        Product clickedData = store.getProducts().get(posisi);
                        Intent intent = new Intent(v.getContext(), DetailMenuActivity.class);
                        intent.putExtra("namaMenu", clickedData.getName());
                        intent.putExtra("kategoriMenu", clickedData.getCategory().getName());
                        intent.putExtra("hargaMenu", numberFormat.format(clickedData.getPrice()).replace(',','.'));
                        intent.putExtra("detailMenu", clickedData.getDescription());
                        intent.putExtra("gambarMenu", clickedData.getPicture());
                        Toast.makeText(v.getContext(), "On Click : " +clickedData.getName(), Toast.LENGTH_LONG);
                        context.startActivity(intent);
                    }
                }
            });
        }

    }
}

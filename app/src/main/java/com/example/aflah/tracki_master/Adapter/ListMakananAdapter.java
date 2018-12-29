package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aflah.tracki_master.View.DetailMenuActivity;
import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListMakananAdapter extends RecyclerView.Adapter<ListMakananAdapter.MyViewHolder> {

    private Context context;
    private List<Product> listMakanan;
    NumberFormat numberFormat = new DecimalFormat("#,###");


    public ListMakananAdapter(Context context, List<Product> listMakanan) {
        this.context = context;
        this.listMakanan = listMakanan;
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
        holder.namaMakanan.setText(listMakanan.get(position).getName());
        holder.hargaMakanan.setText(numberFormat.format(listMakanan.get(position).getPrice()).replace(',','.'));
        Picasso.get().load(listMakanan.get(position).getPicture()).into(holder.img_makanan);
    }

    @Override
    public int getItemCount() {
        return listMakanan.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namaMakanan, hargaMakanan, rpMakanan;
        CircleImageView img_makanan;

        public MyViewHolder(View itemView) {
            super(itemView);

            namaMakanan = (TextView) itemView.findViewById(R.id.tv_namaMakanan_makanan);
            hargaMakanan = (TextView) itemView.findViewById(R.id.tv_hargaMakanan_makanan);
            rpMakanan = (TextView) itemView.findViewById(R.id.tv_rpMakanan_makanan);
            img_makanan = (CircleImageView) itemView.findViewById(R.id.img_makanan_makanan);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posisi = getAdapterPosition();
                    if (posisi != RecyclerView.NO_POSITION){
                        Product clickedData = listMakanan.get(posisi);
                        Intent intent = new Intent(v.getContext(), DetailMenuActivity.class);
                        intent.putExtra("idProduk", clickedData.getId());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}

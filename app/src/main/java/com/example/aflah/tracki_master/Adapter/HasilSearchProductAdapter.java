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

import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.Presenter.DetailTokoPresenter;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.View.DetailMenuActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class HasilSearchProductAdapter extends RecyclerView.Adapter<HasilSearchProductAdapter.MyViewHolder>{
    private Context context;
    private List<Product> listProduct;

    public HasilSearchProductAdapter(Context context, List listProduct){
        this.context = context;
        this.listProduct = listProduct;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hasil_search, parent, false);
        HasilSearchProductAdapter.MyViewHolder myViewHolder = new HasilSearchProductAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NumberFormat numberFormat = new DecimalFormat("#,###");

        holder.nama.setText(listProduct.get(position).getName());
        holder.lokasiOrharga.setText("Rp. "+ numberFormat.format(listProduct.get(position).getPrice()).replace(',','.'));
        Picasso.get().load(listProduct.get(position).getPicture()).into(holder.gambar);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailMenuActivity.class);
                intent.putExtra("idProduk",Integer.valueOf(listProduct.get(position).getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {return listProduct.size();}

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

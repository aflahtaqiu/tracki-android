package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aflah.tracki_master.Model.Makanan;
import com.example.aflah.tracki_master.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerviewMakananAdapter extends RecyclerView.Adapter<RecyclerviewMakananAdapter.MyViewHolder> {

    Context context;
    List<Makanan> makananList;

    public RecyclerviewMakananAdapter(Context context, List<Makanan> makananList) {
        this.context = context;
        this.makananList = makananList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v ;

        v = LayoutInflater.from(context).inflate(R.layout.item_makanan, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewNamaMakanan.setText(makananList.get(position).getNamaMakanan());
        holder.textViewHargaMakanan.setText(makananList.get(position).getHargaMakanan());
        holder.circleImageViewMakanan.setImageResource(makananList.get(position).getFotoMakanan());
    }

    @Override
    public int getItemCount() {
        return makananList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewNamaMakanan;
        private TextView textViewHargaMakanan;
        private CircleImageView circleImageViewMakanan;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewNamaMakanan = (TextView) itemView.findViewById(R.id.tv_namaMakanan);
            textViewHargaMakanan = (TextView) itemView.findViewById(R.id.tv_hargaMakanan);
            circleImageViewMakanan = (CircleImageView) itemView.findViewById(R.id.iv_fotoMakanan);
        }
    }
}

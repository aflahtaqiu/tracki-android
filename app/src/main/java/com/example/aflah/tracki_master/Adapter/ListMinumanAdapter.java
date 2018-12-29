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

import com.example.aflah.tracki_master.View.DetailMenuActivity;
import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListMinumanAdapter extends RecyclerView.Adapter<ListMinumanAdapter.MyViewHolder> {

    private Context context;
    private List<Product> listMinuman;
    NumberFormat numberFormat = new DecimalFormat("#,###");

    public ListMinumanAdapter(Context context, List<Product> listMinuman) {
        this.context = context;
        this.listMinuman = listMinuman;
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

        holder.namaMinuman.setText(listMinuman.get(position).getName());
        holder.hargaMinuaman.setText(numberFormat.format(listMinuman.get(position).getPrice()).replace(',','.'));
        Picasso.get().load(listMinuman.get(position).getPicture()).into(holder.img_minuman);
    }

    @Override
    public int getItemCount() {
        return listMinuman.size();
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
                        Product clickedData = listMinuman.get(posisi);
                        Intent intent = new Intent(v.getContext(), DetailMenuActivity.class);
                        intent.putExtra("idProduk", clickedData.getId());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}

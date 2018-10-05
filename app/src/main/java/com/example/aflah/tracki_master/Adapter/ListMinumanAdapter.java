package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListMinumanAdapter extends RecyclerView.Adapter<ListMinumanAdapter.MyViewHolder> {

    private Context context;
    private Store store;

    public ListMinumanAdapter(Context context, Store store) {
        this.context = context;
        this.store = store;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_minuman, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (store.getProducts().get(position).getCategory().getId() == 2){
            holder.namaMinuman.setText(store.getProducts().get(position).getName());
            holder.hargaMinuaman.setText(String.valueOf((int) store.getProducts().get(position).getPrice()));
        }
        else {
            holder.img_makanan.setVisibility(View.GONE);
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
        CircleImageView img_makanan;
        LinearLayout linearLayout_minuman_item;

        public MyViewHolder(View itemView) {
            super(itemView);

            namaMinuman = (TextView) itemView.findViewById(R.id.tv_namaMimunam_minuman);
            hargaMinuaman = (TextView) itemView.findViewById(R.id.tv_hargaMinuman_minuman);
            rpMinuman = (TextView) itemView.findViewById(R.id.tv_rpminuman_minuman);
            img_makanan = (CircleImageView) itemView.findViewById(R.id.img_minuman_minuman);
            linearLayout_minuman_item = (LinearLayout) itemView.findViewById(R.id.linearlayout_item_minuman);
        }
    }
}

package com.example.aflah.tracki_master.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private List<Store> stores;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_detailtoko, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.namaToko.setText(stores.get(position).getName());
        holder.noTelp.setText(stores.get(position).getPhone());
        holder.lokasi.setText(stores.get(position).getLocation());
        holder.jamBuka.setText(stores.get(position).getOpen());
        holder.jamTutup.setText(stores.get(position).getClose());

        Picasso.with(context)
                .load(stores.get(position).getLogo())
                .placeholder(R.drawable.logo)
                .into(holder.imgToko);
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public StoreAdapter(List<Store> stores, Context context) {
        this.stores = stores;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView namaToko, jamBuka, jamTutup, lokasi, noTelp;
        private CircleImageView imgToko;

        public ViewHolder(View itemView) {
            super(itemView);

            namaToko = (TextView) itemView.findViewById(R.id.tv_namatoko_detailtoko);
            jamBuka = (TextView) itemView.findViewById(R.id.tv_jambuka_detailtoko);
            jamTutup = (TextView) itemView.findViewById(R.id.tv_jamtutup_detailtoko);
            lokasi = (TextView) itemView.findViewById(R.id.tv_lokasitoko_detailtoko);
            noTelp =(TextView) itemView.findViewById(R.id.tv_notelp_detailtoko);

            imgToko = (CircleImageView) itemView.findViewById(R.id.img_detailtoko);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Store clickedData = stores.get(pos);
                        Toast.makeText(v.getContext(), "You clicked " + clickedData.getName(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


}

package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aflah.tracki_master.Model.Makanan;
import com.example.aflah.tracki_master.R;

import java.util.List;

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

//        v = LayoutInflater.from(context).inflate(R.layout.)
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

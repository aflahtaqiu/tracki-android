package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.aflah.tracki_master.Model.Reviewer;
import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    Context context;
    List<Reviewer> reviewers;

    public ReviewAdapter(Context context, List<Reviewer> reviewers) {
        this.context = context;
        this.reviewers = reviewers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_namaUser.setText(reviewers.get(position).getName());
        holder.tv_isiKomentar.setText(reviewers.get(position).getPivot().getDescription());
        holder.ratingBar.setRating((float)reviewers.get(position).getPivot().getRating());
        Picasso.get().load(reviewers.get(position).getFoto()).fit().into(holder.iv_fotoProfil);

    }

    @Override
    public int getItemCount() {
        return reviewers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        CircleImageView iv_fotoProfil;
        TextView tv_namaUser, tv_isiKomentar;
        RatingBar ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardview_review);
            iv_fotoProfil= (CircleImageView) itemView.findViewById(R.id.iv_fotoUser_itemReview);
            tv_namaUser = (TextView) itemView.findViewById(R.id.tv_userName_itemReview);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating_itemReview);
            tv_isiKomentar = (TextView) itemView.findViewById(R.id.tv_komentar_itemReview);
        }
    }

}

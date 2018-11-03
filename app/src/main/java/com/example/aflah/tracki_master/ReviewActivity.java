package com.example.aflah.tracki_master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.Adapter.ReviewAdapter;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    TextView namaToko, tipeToko;
    RecyclerView recyclerView;
    ReviewAdapter reviewAdapter;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        circleImageView = (CircleImageView) findViewById(R.id.imgToko_review);
        namaToko = (TextView) findViewById(R.id.tv_namaToko_review);
        tipeToko = (TextView) findViewById(R.id.tv_tipeToko_review);
        ratingBar = (RatingBar) findViewById(R.id.ratingTokoResult_review);

        recyclerView = (RecyclerView) findViewById(R.id.recycerview_review);

        int idToko = getIntent().getExtras().getInt("idToko");
        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseDetailToko> getData = apiRequest.getStoreByID(idToko);
        getData.enqueue(new Callback<ResponseDetailToko>() {
            @Override
            public void onResponse(Call<ResponseDetailToko> call, Response<ResponseDetailToko> response) {
                namaToko.setText(response.body().getStore().getName());
                tipeToko.setText(response.body().getStore().getStore_type().getName());
                ratingBar.setRating((float)response.body().getStore().getRating());
                Picasso.get().load(response.body().getStore().getLogo()).fit().into(circleImageView);

                recyclerView.setLayoutManager(new GridLayoutManager(ReviewActivity.this, 1));
                reviewAdapter = new ReviewAdapter(ReviewActivity.this, response.body().getStore().getReviewers());
                recyclerView.setAdapter(reviewAdapter);
            }

            @Override
            public void onFailure(Call<ResponseDetailToko> call, Throwable t) {

            }
        });

    }
}

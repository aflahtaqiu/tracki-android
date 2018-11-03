package com.example.aflah.tracki_master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.Adapter.ReviewAdapter;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewTokoActivity extends AppCompatActivity implements View.OnClickListener {

    CircleImageView circleImageView;
    TextView namaToko, tipeToko;
    RecyclerView recyclerView;
    ReviewAdapter reviewAdapter;
    RatingBar ratingBarResult, ratingBarSend;
    Button btnReview;
    EditText et_isiKomentar;
    double ratingTokoSend;
    String komentarToko;
    int idToko;
    ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_toko);

        circleImageView = (CircleImageView) findViewById(R.id.imgToko_review);
        namaToko = (TextView) findViewById(R.id.tv_namaToko_review);
        tipeToko = (TextView) findViewById(R.id.tv_tipeToko_review);
        ratingBarResult = (RatingBar) findViewById(R.id.ratingTokoResult_review);
        ratingBarSend =(RatingBar) findViewById(R.id.ratingBarToko_review);
        et_isiKomentar = (EditText) findViewById(R.id.et_komentarToko_review);
        btnReview = (Button) findViewById(R.id.btn_kirim_review);
        recyclerView = (RecyclerView) findViewById(R.id.recycerview_review);

        btnReview.setOnClickListener(this);

        idToko = getIntent().getExtras().getInt("idToko");
        apiRequest = RetroServer.getClient().create(ApiRequest.class);

        getReview();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_kirim_review:
                Toast.makeText(ReviewTokoActivity.this, " rating: " + ratingBarSend.getRating()+ " isi:" + et_isiKomentar.getText().toString(), Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void getReview(){
        Call<ResponseDetailToko> getData = apiRequest.getStoreByID(idToko);
        getData.enqueue(new Callback<ResponseDetailToko>() {
            @Override
            public void onResponse(Call<ResponseDetailToko> call, Response<ResponseDetailToko> response) {
                namaToko.setText(response.body().getStore().getName());
                tipeToko.setText(response.body().getStore().getStore_type().getName());
                ratingBarResult.setRating((float)response.body().getStore().getRating());
                Picasso.get().load(response.body().getStore().getLogo()).fit().into(circleImageView);

                recyclerView.setLayoutManager(new GridLayoutManager(ReviewTokoActivity.this, 1));
                reviewAdapter = new ReviewAdapter(ReviewTokoActivity.this, response.body().getStore().getReviewers());
                recyclerView.setAdapter(reviewAdapter);
            }

            @Override
            public void onFailure(Call<ResponseDetailToko> call, Throwable t) {

            }
        });
    }
}

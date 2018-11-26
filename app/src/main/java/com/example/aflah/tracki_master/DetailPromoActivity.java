package com.example.aflah.tracki_master;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.Response.ResponsePromotion;
import com.example.aflah.tracki_master.Model.Response.ResponsePromotionById;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPromoActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnGunakan;
    TextView textViewNamaPromo, textViewNamaToko, textViewTanggalPromo, textViewKetentuanPromo,
        textViewDeskripsiPromo;
    int idPromo, idToko;
    String namaToko;
    ImageView gambarPromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo);

        btnGunakan = (Button) findViewById(R.id.btnGunakan_detailPromo);
        textViewNamaPromo = (TextView) findViewById(R.id.namaPromo_detailPromo);
        textViewNamaToko = (TextView) findViewById(R.id.namaToko_detailPromo);
        textViewTanggalPromo = (TextView) findViewById(R.id.batasTanggalPromo_detailPromo);
        textViewDeskripsiPromo = (TextView) findViewById(R.id.deskripsiPromo_detailPromo);
        textViewKetentuanPromo = (TextView) findViewById(R.id.ketentuanPromo_detailPromo);
        gambarPromo = (ImageView) findViewById(R.id.gambarPromo_detailPromo);

        idPromo = getIntent().getExtras().getInt("idPromo");
        idToko = getIntent().getExtras().getInt("idToko");
        namaToko = getIntent().getExtras().getString("namaToko");

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponsePromotionById> getPromotion = apiRequest.getPromotionById(idPromo);
        getPromotion.enqueue(new Callback<ResponsePromotionById>() {
            @Override
            public void onResponse(Call<ResponsePromotionById> call, Response<ResponsePromotionById> response) {
                textViewNamaPromo.setText(response.body().getPromotion().getTitle());
                textViewNamaToko.setText(namaToko);
                textViewTanggalPromo.setText(response.body().getPromotion().getExpired_date());
                textViewDeskripsiPromo.setText(response.body().getPromotion().getDescription());
                textViewKetentuanPromo.setText(response.body().getPromotion().getTerms_and_policies());
                Picasso.get().load(response.body().getPromotion().getBanner()).into(gambarPromo);
            }

            @Override
            public void onFailure(Call<ResponsePromotionById> call, Throwable t) {

            }
        });

        btnGunakan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGunakan_detailPromo :
                startActivity(new Intent(DetailPromoActivity.this, QRCodePromoActivity.class));
                break;
        }
    }
}

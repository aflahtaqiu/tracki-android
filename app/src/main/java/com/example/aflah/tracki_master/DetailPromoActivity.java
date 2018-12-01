package com.example.aflah.tracki_master;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aflah.tracki_master.Model.Response.ResponsePromotionById;
import com.example.aflah.tracki_master.Model.Response.ResponseRedeemPromotion;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPromoActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnGunakan, btnSimpan;
    TextView textViewNamaPromo, textViewNamaToko, textViewTanggalPromo, textViewKetentuanPromo,
        textViewDeskripsiPromo, textViewPromoDigunakan;
    int idPromo;
    String namaToko;
    ImageView gambarPromo;
    UserLogin userLogin;
    SharedPreferences sharedPreferences;
    HashMap<String, Object> hasMapQrCode;
    String qrCodeString;
    String userToken;
    Toolbar toolbarPromoUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo);

        btnGunakan = (Button) findViewById(R.id.btnGunakan_detailPromo);
        btnSimpan = (Button) findViewById(R.id.btnSimpan_detailPromo);
        textViewNamaPromo = (TextView) findViewById(R.id.namaPromo_detailPromo);
        textViewNamaToko = (TextView) findViewById(R.id.namaToko_detailPromo);
        textViewTanggalPromo = (TextView) findViewById(R.id.batasTanggalPromo_detailPromo);
        textViewDeskripsiPromo = (TextView) findViewById(R.id.deskripsiPromo_detailPromo);
        textViewKetentuanPromo = (TextView) findViewById(R.id.ketentuanPromo_detailPromo);
        textViewPromoDigunakan = (TextView) findViewById(R.id.tv_promoDigunakan_detailPromo);
        gambarPromo = (ImageView) findViewById(R.id.gambarPromo_detailPromo);

        idPromo = getIntent().getExtras().getInt("idPromo");
        namaToko = getIntent().getExtras().getString("namaToko");
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userLogin", "");
        userLogin = gson.fromJson(json, UserLogin.class);
        userToken = sharedPreferences.getString("tokenLogin", "");

        hasMapQrCode = new HashMap<>();
        hasMapQrCode.put("idPromo", idPromo);
        hasMapQrCode.put("idUser", userLogin.getId());
        qrCodeString = gson.toJsonTree(hasMapQrCode).toString();

        Log.v("detailPromoAc","token :"+ userToken);
        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponsePromotionById> getPromotion = apiRequest.getPromotionById(userToken,idPromo);
        getPromotion.enqueue(new Callback<ResponsePromotionById>() {
            @Override
            public void onResponse(Call<ResponsePromotionById> call, Response<ResponsePromotionById> response) {

                textViewNamaPromo.setText(response.body().getPromotion().getTitle());
                textViewNamaToko.setText(namaToko);

                try {
                    String expireDateStr;
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateExpire = inputFormat.parse(response.body().getPromotion().getExpired_date());
                    expireDateStr = outputFormat.format(dateExpire);
                    textViewTanggalPromo.setText(expireDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                textViewDeskripsiPromo.setText(response.body().getPromotion().getDescription());
                textViewKetentuanPromo.setText(response.body().getPromotion().getTerms_and_policies());
                Picasso.get().load(response.body().getPromotion().getBanner()).into(gambarPromo);

                if (response.body().getPromotion().getSaved() == true){
                    btnSimpan.setEnabled(false);
                    if (response.body().getPromotion().getUsed() == true){
                        btnSimpan.setVisibility(View.GONE);
                        btnGunakan.setVisibility(View.GONE);
                        textViewPromoDigunakan.setVisibility(View.VISIBLE);
                    }
                }
                if (response.body().getPromotion().getUsed() == true){
                    btnGunakan.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<ResponsePromotionById> call, Throwable t) {

            }
        });

        btnGunakan.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGunakan_detailPromo :
                Intent intent = new Intent(DetailPromoActivity.this, QRCodePromoActivity.class);
                intent.putExtra("qrCodeString", qrCodeString);
                intent.putExtra("", idPromo);
                startActivity(intent);
                break;
            case R.id.btnSimpan_detailPromo :

                ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                Call<ResponseRedeemPromotion> getReedem = apiRequest.getRedeemData(userToken, idPromo);
                getReedem.enqueue(new Callback<ResponseRedeemPromotion>() {
                    @Override
                    public void onResponse(Call<ResponseRedeemPromotion> call, Response<ResponseRedeemPromotion> response) {
                        Log.v("sudahRedeem", "anda sudah redeem promo ini " + response.toString());
                    }

                    @Override
                    public void onFailure(Call<ResponseRedeemPromotion> call, Throwable t) {
                        Log.v("sudahRedeem", "anda onFailure " + t.getMessage());
                    }
                });
                btnSimpan.setEnabled(false);
//                btnGunakan.setEnabled(true);
//                btnGunakan.setBackground(this.getResources().getDrawable(R.drawable.button_gunakan));
                break;
        }
    }
}

package com.example.aflah.tracki_master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aflah.tracki_master.Model.ResponseProductById;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMenuActivity extends AppCompatActivity {

    TextView namaMenu, kategoriMenu, hargaMenu, detailMenu;
    ImageView gambarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);

        namaMenu = (TextView) findViewById(R.id.tv_namaMenu_detailMenu);
        kategoriMenu = (TextView) findViewById(R.id.tv_kategoriMenu_detailMenu);
        hargaMenu = (TextView) findViewById(R.id.tv_hargaMenu_detailMenu);
        detailMenu = (TextView) findViewById(R.id.tv_detailMenu_detailMenu);
        gambarMenu = (ImageView) findViewById(R.id.iv_menu_detailMenu);


        try{
            int idProduk = getIntent().getExtras().getInt("idProduk");
            ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
            Call<ResponseProductById> getproduk = apiRequest.getProductById(idProduk);
            getproduk.enqueue(new Callback<ResponseProductById>() {
                @Override
                public void onResponse(Call<ResponseProductById> call, Response<ResponseProductById> response) {
                    NumberFormat numberFormat = new DecimalFormat("#,###");
                    String nama = response.body().getProduct().getName();
                    String kategori = response.body().getProduct().getCategory().getName();
                    String harga = numberFormat.format(response.body().getProduct().getPrice()).replace(',','.');
                    String detail = response.body().getProduct().getDescription();
                    String gambar = response.body().getProduct().getPicture();
                    namaMenu.setText(nama);
                    kategoriMenu.setText(kategori);
                    hargaMenu.setText(harga);
                    detailMenu.setText(detail);
                    Picasso.get().load(gambar).into(gambarMenu);
                }

                @Override
                public void onFailure(Call<ResponseProductById> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

    }
}

package com.example.aflah.tracki_master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

        String nama = getIntent().getExtras().getString("namaMenu");
        String kategori = getIntent().getExtras().getString("kategoriMenu");
        String harga = getIntent().getExtras().getString("hargaMenu");
        String detail = getIntent().getExtras().getString("detailMenu");
        String gambar = getIntent().getExtras().getString("gambarMenu");

        namaMenu.setText(nama);
        kategoriMenu.setText(kategori);
        hargaMenu.setText(harga);
        detailMenu.setText(detail);
        Picasso.get().load(gambar).into(gambarMenu);
    }
}

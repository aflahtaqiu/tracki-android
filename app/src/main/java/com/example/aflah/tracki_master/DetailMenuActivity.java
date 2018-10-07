package com.example.aflah.tracki_master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetailMenuActivity extends AppCompatActivity {

    TextView namaMenu, kategoriMenu, hargaMenu, detailMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);

        namaMenu = (TextView) findViewById(R.id.tv_namaMenu_detailMenu);
        kategoriMenu = (TextView) findViewById(R.id.tv_kategoriMenu_detailMenu);
        hargaMenu = (TextView) findViewById(R.id.tv_hargaMenu_detailMenu);
        detailMenu = (TextView) findViewById(R.id.tv_detailMenu_detailMenu);

        String nama = getIntent().getExtras().getString("namaMenu");
        String kategori = getIntent().getExtras().getString("kategoriMenu");
        String harga = getIntent().getExtras().getString("hargaMenu");
        String detail = getIntent().getExtras().getString("detailMenu");


        namaMenu.setText(nama);
        kategoriMenu.setText(kategori);
        hargaMenu.setText(harga);
        detailMenu.setText(detail);

    }
}

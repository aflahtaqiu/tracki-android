package com.example.aflah.tracki_master.View.Toko;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.example.aflah.tracki_master.R;

public class DetailTokoActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_toko);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.favourite);

    }
}

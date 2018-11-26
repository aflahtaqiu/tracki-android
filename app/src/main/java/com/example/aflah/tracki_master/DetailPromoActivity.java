package com.example.aflah.tracki_master;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailPromoActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnGunakan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo);

        btnGunakan = (Button) findViewById(R.id.btnGunakan_detailPromo);

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

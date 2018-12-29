package com.example.aflah.tracki_master.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.Contract.DetailMenuContract;
import com.example.aflah.tracki_master.Presenter.DetailMenuPresenter;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.ResponseProductById;
import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DetailMenuActivity extends AppCompatActivity implements DetailMenuContract.view {

    TextView namaMenu, kategoriMenu, hargaMenu, detailMenu, tvDetail, tvRp;
    ImageView gambarMenu, icHarga;
    ProgressBar progressBar;

    private DetailMenuPresenter presenter = new DetailMenuPresenter(Injection.provideProductRepository(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);

        initViews();
        invisibleVIews();
        int idProduk = getIntent().getExtras().getInt("idProduk");
        presenter.getDetailProduct(idProduk);
    }

    private void invisibleVIews() {
        tvDetail.setVisibility(View.INVISIBLE);
        tvRp.setVisibility(View.INVISIBLE);
        icHarga.setVisibility(View.INVISIBLE);
    }

    private void initViews() {
        namaMenu = (TextView) findViewById(R.id.tv_namaMenu_detailMenu);
        kategoriMenu = (TextView) findViewById(R.id.tv_kategoriMenu_detailMenu);
        hargaMenu = (TextView) findViewById(R.id.tv_hargaMenu_detailMenu);
        detailMenu = (TextView) findViewById(R.id.tv_detailMenu_detailMenu);
        gambarMenu = (ImageView) findViewById(R.id.iv_menu_detailMenu);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_detailProduk);
        tvDetail = (TextView) findViewById(R.id.tv_detail_detailMenu);
        tvRp = (TextView) findViewById(R.id.tv_rpMenu_detailMenu);
        icHarga = (ImageView) findViewById(R.id.iv_icHarga_detailMenu);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showData(ResponseProductById productNew) {

        NumberFormat numberFormat = new DecimalFormat("#,###");

        namaMenu.setText(productNew.getProduct().getName());
        kategoriMenu.setText(productNew.getProduct().getCategory().getName());
        hargaMenu.setText(numberFormat.format(productNew.getProduct().getPrice()).replace(',','.'));
        detailMenu.setText(productNew.getProduct().getDescription());
        detailMenu.setBackgroundResource(R.drawable.border_text);
        Picasso.get().load(productNew.getProduct().getPicture()).into(gambarMenu);
        tvDetail.setVisibility(View.VISIBLE);
        tvRp.setVisibility(View.VISIBLE);
        icHarga.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String errMsg) {
        Toast.makeText(DetailMenuActivity.this, "Errornya " + errMsg, Toast.LENGTH_LONG).show();
    }
}

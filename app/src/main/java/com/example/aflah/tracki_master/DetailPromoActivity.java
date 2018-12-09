package com.example.aflah.tracki_master;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aflah.tracki_master.Model.Response.ResponsePromotionById;
import com.example.aflah.tracki_master.Model.Response.ResponseRedeemPromotion;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPromoActivity extends AppCompatActivity implements View.OnClickListener{

    final Context context = this;
    Button btnGunakan, btnSimpan;
    TextView textViewNamaPromo, textViewNamaToko, textViewTanggalPromo, textViewKetentuanPromo,
        textViewDeskripsiPromo, textViewPromoDigunakan, textViewTanggalTersedia, textViewdeskripsi, textViewSyaratKetentuan;
    int idPromo;
    String namaToko;
    ImageView gambarPromo;
    UserLogin userLogin;
    SharedPreferences sharedPreferences;
    HashMap<String, Object> hasMapQrCode;
    String qrCodeString;
    String userToken;
    ProgressBar progressBarDetailPromo;
    View view1, view2, view3;
    SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo);

        initView();

        initViewGone();

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

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponsePromotionById> getPromotion = apiRequest.getPromotionById(userToken,idPromo);
        getPromotion.enqueue(new Callback<ResponsePromotionById>() {
            @Override
            public void onResponse(Call<ResponsePromotionById> call, Response<ResponsePromotionById> response) {

                progressBarDetailPromo.setVisibility(View.GONE);

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

                btnSimpan.setVisibility(View.VISIBLE);
                btnGunakan.setVisibility(View.VISIBLE);
                textViewTanggalTersedia.setVisibility(View.VISIBLE);
                textViewdeskripsi.setVisibility(View.VISIBLE);
                textViewSyaratKetentuan.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.VISIBLE);
                gambarPromo.setVisibility(View.VISIBLE);

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

    private void initViewGone() {
        btnSimpan.setVisibility(View.INVISIBLE);
        btnGunakan.setVisibility(View.INVISIBLE);
        gambarPromo.setVisibility(View.INVISIBLE);
        textViewTanggalTersedia.setVisibility(View.INVISIBLE);
        textViewdeskripsi.setVisibility(View.INVISIBLE);
        textViewSyaratKetentuan.setVisibility(View.INVISIBLE);
        view1.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.INVISIBLE);
        view3.setVisibility(View.INVISIBLE);
    }

    private void initView() {
        btnGunakan = (Button) findViewById(R.id.btnGunakan_detailPromo);
        btnSimpan = (Button) findViewById(R.id.btnSimpan_detailPromo);
        textViewNamaPromo = (TextView) findViewById(R.id.namaPromo_detailPromo);
        textViewNamaToko = (TextView) findViewById(R.id.namaToko_detailPromo);
        textViewTanggalPromo = (TextView) findViewById(R.id.batasTanggalPromo_detailPromo);
        textViewDeskripsiPromo = (TextView) findViewById(R.id.deskripsiPromo_detailPromo);
        textViewKetentuanPromo = (TextView) findViewById(R.id.ketentuanPromo_detailPromo);
        textViewPromoDigunakan = (TextView) findViewById(R.id.tv_promoDigunakan_detailPromo);
        textViewTanggalTersedia = (TextView) findViewById(R.id.tanggalPromo);
        textViewdeskripsi = (TextView) findViewById(R.id.deskripsi);
        textViewSyaratKetentuan = (TextView) findViewById(R.id.syaratKetentuan);
        gambarPromo = (ImageView) findViewById(R.id.gambarPromo_detailPromo);
        view1 = (View) findViewById(R.id.view1);
        view2 = (View) findViewById(R.id.view2);
        view3 = (View) findViewById(R.id.view3);
        progressBarDetailPromo = (ProgressBar) findViewById(R.id.progressBarDetailPromo);

        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Promo disimpan")
                .setContentText("Silahkan lihat di halaman akun");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGunakan_detailPromo :
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_qrcode_promo);

                ImageView qrCode = (ImageView) dialog.findViewById(R.id.qrCode_generatePromo);

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(qrCodeString, BarcodeFormat.QR_CODE,250, 250 );
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    qrCode.setImageBitmap(bitmap);
                }catch (WriterException e) {
                    e.printStackTrace();
                }

                Button btnClose = (Button) dialog.findViewById(R.id.btnClosePopUp);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

                break;
            case R.id.btnSimpan_detailPromo :

                ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                Call<ResponseRedeemPromotion> getReedem = apiRequest.getRedeemData(userToken, idPromo);
                getReedem.enqueue(new Callback<ResponseRedeemPromotion>() {
                    @Override
                    public void onResponse(Call<ResponseRedeemPromotion> call, Response<ResponseRedeemPromotion> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseRedeemPromotion> call, Throwable t) {
                        Log.v("sudahRedeem", "anda onFailure " + t.getMessage());
                    }
                });

                sweetAlertDialog.show();
                btnSimpan.setEnabled(false);
                break;
        }
    }
}

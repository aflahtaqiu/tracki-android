package com.example.aflah.tracki_master.DetailPromo;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.R;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailPromoActivity extends AppCompatActivity implements  DetailPromoContract.view, View.OnClickListener{

    Button btnGunakan, btnSimpan, btnCloseDialogQRCode;
    TextView textViewNamaPromo, textViewNamaToko, textViewTanggalPromo, textViewKetentuanPromo,
        textViewDeskripsiPromo, textViewPromoDigunakan;
    int idPromo;
    ConstraintLayout constraintLayout;
    String namaToko;
    ImageView gambarPromo;
    UserLogin userLogin;
    SharedPreferences sharedPreferences;
    HashMap<String, Object> hasMapQrCode;
    String qrCodeString,userToken;
    SweetAlertDialog dialogProgress,dialogSuccess;
    Dialog qrCodeDialog;

    private DetailPromoPresenter presenter = new DetailPromoPresenter(Injection.provideDetailPromoRepository(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo);

        initView();

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

        presenter.getDetailPromo(userToken, idPromo);

        btnGunakan.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
    }

    private void initView() {
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayoutDetailPromo);
        btnGunakan = (Button) findViewById(R.id.btnGunakan_detailPromo);
        btnSimpan = (Button) findViewById(R.id.btnSimpan_detailPromo);
        textViewNamaPromo = (TextView) findViewById(R.id.namaPromo_detailPromo);
        textViewNamaToko = (TextView) findViewById(R.id.namaToko_detailPromo);
        textViewTanggalPromo = (TextView) findViewById(R.id.batasTanggalPromo_detailPromo);
        textViewDeskripsiPromo = (TextView) findViewById(R.id.deskripsiPromo_detailPromo);
        textViewKetentuanPromo = (TextView) findViewById(R.id.ketentuanPromo_detailPromo);
        textViewPromoDigunakan = (TextView) findViewById(R.id.tv_promoDigunakan_detailPromo);
        gambarPromo = (ImageView) findViewById(R.id.gambarPromo_detailPromo);

        constraintLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGunakan_detailPromo :
                presenter.generateQRCode();
                break;
            case R.id.btnSimpan_detailPromo :
                presenter.sendRedeemData(userToken, idPromo);
                btnSimpan.setEnabled(false);
                break;
        }
    }

    @Override
    public void showProgress() {
        dialogProgress = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialogProgress.getProgressHelper().setBarColor(Color.parseColor("#B40037"));
        dialogProgress.getProgressHelper().setRimColor(Color.parseColor("#B40037"));
        dialogProgress.setTitleText("Loading");
        dialogProgress.setCancelable(false);
        dialogProgress.setCanceledOnTouchOutside(true);
        dialogProgress.show();
    }

    @Override
    public void hideProgress() {
        dialogProgress.dismiss();
    }

    @Override
    public void showDialogSuccess() {
        dialogSuccess = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Promo disimpan")
                .setContentText("Silahkan lihat di halaman akun");
        dialogSuccess.show();
    }

    @Override
    public void hideDialogSuccess() {
        dialogSuccess.dismiss();
    }

    @Override
    public void showDialogQRCode() {
        qrCodeDialog = new Dialog(this);
        qrCodeDialog.setContentView(R.layout.activity_qrcode_promo);
        ImageView qrCode = (ImageView) qrCodeDialog.findViewById(R.id.qrCode_generatePromo);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(qrCodeString, BarcodeFormat.QR_CODE,250, 250 );
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);
        }catch (WriterException e) {
            e.printStackTrace();
        }

        btnCloseDialogQRCode = (Button) qrCodeDialog.findViewById(R.id.btnClosePopUp);

        btnCloseDialogQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrCodeDialog.dismiss();
            }
        });

        qrCodeDialog.show();
    }

    @Override
    public void showData(Promotion promotion) {
        textViewNamaPromo.setText(promotion.getTitle());
        textViewNamaToko.setText(namaToko);

        try {
            String expireDateStr;
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateExpire = inputFormat.parse(promotion.getExpired_date());
            expireDateStr = outputFormat.format(dateExpire);
            textViewTanggalPromo.setText(expireDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        textViewDeskripsiPromo.setText(promotion.getDescription());
        textViewKetentuanPromo.setText(promotion.getTerms_and_policies());
        Picasso.get().load(promotion.getBanner()).into(gambarPromo);

        if (promotion.getSaved() == true){
            btnSimpan.setEnabled(false);
            if (promotion.getUsed() == true){
                btnSimpan.setVisibility(View.GONE);
                btnGunakan.setVisibility(View.GONE);
                textViewPromoDigunakan.setVisibility(View.VISIBLE);
            }
        }
        if (promotion.getUsed() == true){
            btnGunakan.setEnabled(false);
        }
        constraintLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this, "Ada error : " + errorMsg, Toast.LENGTH_LONG).show();
    }
}

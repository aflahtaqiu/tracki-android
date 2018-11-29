package com.example.aflah.tracki_master;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

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

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRCodePromoActivity extends AppCompatActivity {

    ImageView imageViewQRCode;
    String generateQRCode;
    SharedPreferences sharedPreferences;
    int idPromo;
    String userToken;
    UserLogin userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_promo);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        getWindow().setLayout((int)(displayMetrics.widthPixels*.8), (int)(displayMetrics.heightPixels*.6));

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userLogin", "");
        userLogin = gson.fromJson(json, UserLogin.class);
        userToken = sharedPreferences.getString("tokenLogin", "");

        imageViewQRCode = (ImageView) findViewById(R.id.qrCode_generatePromo);
        generateQRCode = getIntent().getExtras().getString("qrCodeString");
        idPromo = getIntent().getExtras().getInt("idPromo");

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(generateQRCode, BarcodeFormat.QR_CODE,250, 250 );
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageViewQRCode.setImageBitmap(bitmap);
//            ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
//            Call<ResponseRedeemPromotion> getReedem = apiRequest.getRedeemData(userToken, idPromo);
//            getReedem.enqueue(new Callback<ResponseRedeemPromotion>() {
//                @Override
//                public void onResponse(Call<ResponseRedeemPromotion> call, Response<ResponseRedeemPromotion> response) {
//                    Log.v("sudahRedeem", "anda sudah redeem promo ini " + response.toString());
//                    try {
//                        Toast.makeText(QRCodePromoActivity.this, " " + response.errorBody().string(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseRedeemPromotion> call, Throwable t) {
//                    Log.v("sudahRedeem", "anda sudah redeem promo ini " + t.getMessage());
//                }
//            });
        }catch (WriterException e){
            e.printStackTrace();
        }

    }
}

package com.example.aflah.tracki_master;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    Button imageButtonCLose;

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
        imageButtonCLose = (Button) findViewById(R.id.btnClosePopUp);
        generateQRCode = getIntent().getExtras().getString("qrCodeString");
        idPromo = getIntent().getExtras().getInt("idPromo");

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(generateQRCode, BarcodeFormat.QR_CODE,250, 250 );
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageViewQRCode.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }

        imageButtonCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

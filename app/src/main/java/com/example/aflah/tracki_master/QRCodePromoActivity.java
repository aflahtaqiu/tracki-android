package com.example.aflah.tracki_master;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aflah.tracki_master.Model.Response.ResponsePromotion;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRCodePromoActivity extends AppCompatActivity {

    ImageView imageViewQRCode;
    String generateQRCode;
    int idPromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_promo);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        getWindow().setLayout((int)(displayMetrics.widthPixels*.8), (int)(displayMetrics.heightPixels*.6));

        imageViewQRCode = (ImageView) findViewById(R.id.qrCode_generatePromo);
        generateQRCode = getIntent().getExtras().getString("qrCodeString");
        idPromo = getIntent().getExtras().getInt("idPromo");

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            Log.v("generateQRCode : ", generateQRCode);
            BitMatrix bitMatrix = multiFormatWriter.encode(generateQRCode, BarcodeFormat.QR_CODE,250, 250 );
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageViewQRCode.setImageBitmap(bitmap);
            ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
            Call<ResponsePromotion> getReedem = apiRequest.getRedeemData(idPromo);
            getReedem.enqueue(new Callback<ResponsePromotion>() {
                @Override
                public void onResponse(Call<ResponsePromotion> call, Response<ResponsePromotion> response) {

                }

                @Override
                public void onFailure(Call<ResponsePromotion> call, Throwable t) {

                }
            });
        }catch (WriterException e){
            e.printStackTrace();
        }

    }
}

package com.example.aflah.tracki_master;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodePromoActivity extends AppCompatActivity {

    ImageView imageViewQRCode;
    String generateQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_promo);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        getWindow().setLayout((int)(displayMetrics.widthPixels*.8), (int)(displayMetrics.heightPixels*.6));

        imageViewQRCode = (ImageView) findViewById(R.id.qrCode_generatePromo);
        generateQRCode = getIntent().getExtras().getString("qrCodeString");
        Log.v("qrCodeString", " "+generateQRCode);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(generateQRCode, BarcodeFormat.QR_CODE,250, 250 );
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageViewQRCode.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }

    }
}

package com.example.aflah.tracki_master;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.Model.UserLogin;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Url;


public class EditProfilActivity extends Activity implements View.OnClickListener {

    ImageView avatar, correct, close;
    TextView et_editText;
    Dialog Mydialog;
    File f;
    TextView picAvatar, picGaleri, picKamera;
    Bitmap bitmap;
    EditText etNama, etPassword, etTanggalLahir;
    SharedPreferences sharedPreferences;
    String partImage;
    MultipartBody.Part multipartBody;
    private int REQ_GALLERY = 1;
    private int REQ_CAMERA = 0;
    private static final String IMAGE_DIRECTORY = "/Tracki";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userLogin", "");
        UserLogin userLogin = gson.fromJson(json, UserLogin.class);
        String userToken = sharedPreferences.getString("tokenLogin", "");

        avatar = (CircleImageView)findViewById(R.id.iv_profil_editProfil);
        et_editText = (TextView)findViewById(R.id.tv_ubahFoto_editProfil);
        correct = (ImageView) findViewById(R.id.iv_correctEditProfil);
        close = (ImageView) findViewById(R.id.iv_closeEditProfil);
        etNama = (EditText) findViewById(R.id.et_nama_editProfil);
        etPassword = (EditText) findViewById(R.id.et_sandi_editProfil);
        etTanggalLahir = (EditText) findViewById(R.id.et_tanggalLahir_editProfil);

        Picasso.get().load(userLogin.getFoto()).fit().into(avatar);
        etNama.setText(userLogin.getName());
        etTanggalLahir.setText(userLogin.getDateOfBirth());

        correct.setOnClickListener(this);
        close.setOnClickListener(this);
        avatar.setOnClickListener(this);
        et_editText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_correctEditProfil:
                Toast.makeText(EditProfilActivity.this,"ceklis bro" + bitmap.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_closeEditProfil:
                Toast.makeText(EditProfilActivity.this,"close bro", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_profil_editProfil:
                MyCustomDialog();
                break;
            case R.id.tv_ubahFoto_editProfil:
                MyCustomDialog();
                break;
        }
    }

    public void onStart() {
        super.onStart();

        EditText txtDate = (EditText) findViewById(R.id.et_tanggalLahir_editProfil);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    DateDialog dialog = new DateDialog(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });
    }

    public void MyCustomDialog(){
        Mydialog = new Dialog(EditProfilActivity.this);
        Mydialog.setContentView(R.layout.popup_normal);

        picAvatar = (TextView)Mydialog.findViewById(R.id.imgAvatar);
        picGaleri = (TextView)Mydialog.findViewById(R.id.imgGaleri);
        picKamera = (TextView)Mydialog.findViewById(R.id.imgCamera);

        picAvatar.setEnabled(true);
        picGaleri.setEnabled(true);
        picKamera.setEnabled(true);

        picAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pilih default avatar", Toast.LENGTH_LONG).show();
            }
        });

        picGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Open Gallery"), REQ_GALLERY);
            }
        });

        picKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, REQ_CAMERA);
            }
        });
        Mydialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQ_CAMERA){
                Uri dataImage = data.getData();
                String[] imageProjection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(dataImage, imageProjection, null, null, null);

                if (cursor!=null){
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageProjection[0]);
                    partImage = cursor.getString(indexImage);

                    if (partImage!=null){
                        File image = new File(partImage);
                        avatar.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
                    }
                }
            }
        }
//        if (requestCode == GALLERY) {
//            if (data != null) {
//                Uri contentURI = data.getData();
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                    partImage = saveImage(bitmap);
//                    Toast.makeText(EditProfilActivity.this, "Gambar tersimpan", Toast.LENGTH_SHORT).show();
//                    avatar.setImageBitmap(bitmap);
//                    Mydialog.hide();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(EditProfilActivity.this, "Gambar gagal tersimpan", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
    }

//    public String saveImage(Bitmap myBitmap) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File wallpaperDirectory = new File(
//                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
//        if (!wallpaperDirectory.exists()) {
//            wallpaperDirectory.mkdirs();
//        }
//
//        try {
//            f = new File(wallpaperDirectory, Calendar.getInstance()
//                    .getTimeInMillis() + ".jpg");
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//            MediaScannerConnection.scanFile(this,
//                    new String[]{f.getPath()},
//                    new String[]{"image/jpeg"}, null);
//            fo.close();
//            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
//
//            return f.getAbsolutePath();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        return "";
//    }

}

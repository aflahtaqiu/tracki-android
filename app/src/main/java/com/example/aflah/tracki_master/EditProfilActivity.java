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

import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.NavbarFragment.AccountFragment;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;


public class EditProfilActivity extends Activity implements View.OnClickListener {

    ImageView correct, close;
    EditText etNama, etPassword, etTanggalLahir;
    SharedPreferences sharedPreferences;
    UserLogin userLogin;
    String userToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userLogin", "");
        userLogin = gson.fromJson(json, UserLogin.class);
        userToken = sharedPreferences.getString("tokenLogin", "");

        correct = (ImageView) findViewById(R.id.iv_correctEditProfil);
        close = (ImageView) findViewById(R.id.iv_closeEditProfil);
        etNama = (EditText) findViewById(R.id.et_nama_editProfil);
        etPassword = (EditText) findViewById(R.id.et_sandi_editProfil);
        etTanggalLahir = (EditText) findViewById(R.id.et_tanggalLahir_editProfil);

        etNama.setText(userLogin.getName());
        etTanggalLahir.setText(userLogin.getDateOfBirth());

        correct.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_correctEditProfil:
                Date dateOfBirth;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    dateOfBirth = format.parse(etTanggalLahir.getText().toString());
                    ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                    Call<ResponseUserById> getResultEdit = apiRequest.updateProfile(userLogin.getId(), userToken, etNama.getText().toString(), dateOfBirth);
                    getResultEdit.enqueue(new Callback<ResponseUserById>() {
                        @Override
                        public void onResponse(Call<ResponseUserById> call, Response<ResponseUserById> response) {
                            Log.v("updateProfil", " " + response.toString());
                            Intent intent = new Intent(EditProfilActivity.this, NavigationActivity.class);
                            intent.putExtra("AccountFragmentLoc", R.id.navigation_account);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<ResponseUserById> call, Throwable t) {
                            Log.v("updateProfil", " " + t.getMessage());
                        }
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.iv_closeEditProfil:
                Toast.makeText(EditProfilActivity.this,"close bro", Toast.LENGTH_SHORT).show();
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
}

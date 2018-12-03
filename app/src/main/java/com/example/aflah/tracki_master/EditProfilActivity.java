package com.example.aflah.tracki_master;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.User;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = inputFormat.parse(userLogin.getDateOfBirth());
            String dateStr = outputFormat.format(date);
            etTanggalLahir.setText(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        correct.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_correctEditProfil:

                String date = etTanggalLahir.getText().toString();
                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date dateOfBirth = inputFormat.parse(date);

                    ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                    Call<ResponseUserById> editProfil = apiRequest.updateProfile(userLogin.getId(),userToken, etNama.getText().toString(), dateOfBirth);
                    editProfil.enqueue(new Callback<ResponseUserById>() {
                        @Override
                        public void onResponse(Call<ResponseUserById> call, Response<ResponseUserById> response) {
                            ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                            Call<ResponseUserById> relog = apiRequest.getSavedPromo(userLogin.getId());
                            relog.enqueue(new Callback<ResponseUserById>() {
                                @Override
                                public void onResponse(Call<ResponseUserById> call, Response<ResponseUserById> response) {
                                    User userLogin =  response.body().getUser();

                                    Gson gson = new Gson();
                                    String json = gson.toJson(userLogin);
                                    SharedPreferences.Editor editor = getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                                    editor.putString("userLogin", json);
                                    editor.apply();
                                    editor.commit();

                                    Intent intent = new Intent(getApplicationContext(),NavigationActivity.class);
                                    intent.putExtra("LOC",R.id.navigation_account);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<ResponseUserById> call, Throwable t) {

                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<ResponseUserById> call, Throwable t) {

                        }
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_closeEditProfil:
                finish();
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

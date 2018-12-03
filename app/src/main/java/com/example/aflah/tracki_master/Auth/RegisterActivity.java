package com.example.aflah.tracki_master.Auth;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.DateDialog;
import com.example.aflah.tracki_master.Model.Response.ResponseRegister;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.NavigationActivity;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.omidh.ripplevalidatoredittext.RVEValidatorFactory;
import me.omidh.ripplevalidatoredittext.RVEValidatorType;
import me.omidh.ripplevalidatoredittext.RippleValidatorEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements IRegister, View.OnClickListener {

    Button btnDaftar;
    TextView tv_masuk;
    RippleValidatorEditText etNama, etEmail, etSandi, etSandiKonfirmasi, etTanggalLahir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNama = (RippleValidatorEditText) findViewById(R.id.et_nama_register);
        etEmail = (RippleValidatorEditText) findViewById(R.id.et_email_register);
        etSandi = (RippleValidatorEditText) findViewById(R.id.et_sandi_register);
        etSandiKonfirmasi = (RippleValidatorEditText) findViewById(R.id.et_konfirmSandi_register);
        etTanggalLahir = (RippleValidatorEditText) findViewById(R.id.et_tanggalLahir_register);
        tv_masuk = (TextView) findViewById(R.id.tv_masuk_register);
        btnDaftar = (Button) findViewById(R.id.btn_daftar_register);

        etNama.addValidator(
                RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY,"Anda belum mengisi nama Anda",null)
        );
        etEmail.addValidator(
                RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Anda belum mengisi email Anda", null),
                RVEValidatorFactory.getValidator(RVEValidatorType.EMAIL, "ex : john@doe.com", null)
        );
        etSandi.addValidator(
                RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Anda belum mengisi sandi Anda", null),
                RVEValidatorFactory.getValidator(RVEValidatorType.MIN_LENGTH, "Password minimal 8 karakter", 8)
        );
        etSandiKonfirmasi.addValidator(
                RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Anda belum mengisi konfirmasi sandi Anda", null)
        );
        etTanggalLahir.addValidator(
                RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Isi tanggal Lahir Anda", null)
        );

        btnDaftar.setOnClickListener(this);
        tv_masuk.setOnClickListener(this);
    }

    public void onStart() {
        super.onStart();

        RippleValidatorEditText txtDate = (RippleValidatorEditText) findViewById(R.id.et_tanggalLahir_register);
        txtDate.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    DateDialog dialog = new DateDialog(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_daftar_register:
                if (cekValidasi()){
                    String nama = etNama.getText().toString();
                    String email = etEmail.getText().toString();
                    String password = etSandi.getText().toString();
                    String konfirmasiPassword = etSandiKonfirmasi.getText().toString();
                    String date = etTanggalLahir.getText().toString();

                    try {
                        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Date dateOfBirth = inputFormat.parse(date);

                        signupUserEmail(nama, email, dateOfBirth, password, konfirmasiPassword);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.tv_masuk_register:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
    }

    @Override
    public boolean cekValidasi() {

        if (etNama.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY,"Anda belum mengisi nama Anda",null),true) &&
                etEmail.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Anda belum mengisi email Anda", null),true) &&
                etSandi.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Anda belum mengisi sandi Anda", null),true) &&
                etSandiKonfirmasi.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Anda belum mengisi konfirmasi sandi Anda", null),true) &&
                etTanggalLahir.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Isi tanggal Lahir Anda", null),true)){
            if (etEmail.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMAIL, "ex : john@doe.com", null),true) &&
                    etSandi.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.MIN_LENGTH, "Password minimal 8 karakter", 8), true) &&
                    etSandiKonfirmasi.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EQUAL, "Tidak cocok dengan sandi Anda", etSandi.getText().toString()),true)){
                return true;
            }
            else return false;
        }
        else return false;
    }

    @Override
    public void signupUserEmail(String name, String email, Date dateOfBirth, String password, String konfirmasiPassword) {

        ApiRequest apiRequest = RetroServer.getRegister().create(ApiRequest.class);
        Call<ResponseRegister> registerUser = apiRequest.sendRegister(name, email, dateOfBirth, password, konfirmasiPassword);
        registerUser.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                try {
                    UserLogin userLogin = response.body().getUser();
                    String token = response.body().getAccess_token();

                    Gson gson = new Gson();
                    String json = gson.toJson(userLogin);
                    SharedPreferences.Editor editor = getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                    editor.putString("tokenLogin","Bearer "+ token);
                    editor.putString("userLogin", json);
                    editor.apply();
                    editor.commit();
                    Log.e("selamatDatangRegister", response.body().toString());
                    startActivity(new Intent(RegisterActivity.this, NavigationActivity.class));
                    finish();
                }catch (Exception e){
                    Toast.makeText(RegisterActivity.this, "Anda sudah terdaftar", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Maaf email Anda sudah terdaftar", Toast.LENGTH_LONG).show();
                etEmail.getEditText().setText("");
            }
        });

    }
}

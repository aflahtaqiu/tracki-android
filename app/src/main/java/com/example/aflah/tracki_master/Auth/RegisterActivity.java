package com.example.aflah.tracki_master.Auth;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.DateDialog;
import com.example.aflah.tracki_master.Model.Response.ResponseRegister;
import com.example.aflah.tracki_master.NavigationActivity;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements IRegister, View.OnClickListener {

    Button btnDaftar;
    EditText et_nama, et_email, et_sandi, et_konfirmSandi, et_tanggalLahir;
    TextView tv_masuk;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_nama = (EditText) findViewById(R.id.et_nama_register);
        et_email = (EditText) findViewById(R.id.et_email_register);
        et_sandi = (EditText) findViewById(R.id.et_sandi_register); //email harus minimal 6 karakter
        et_konfirmSandi = (EditText) findViewById(R.id.et_konfirmSandi_register);
        et_tanggalLahir = (EditText) findViewById(R.id.et_tanggalLahir_register);
        tv_masuk = (TextView) findViewById(R.id.tv_masuk_register);
        btnDaftar = (Button) findViewById(R.id.btn_daftar_register);

        mAuth = FirebaseAuth.getInstance();

        et_konfirmSandi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = et_sandi.getText().toString();
                if (!s.toString().equals(password)){
                    et_konfirmSandi.setError("Password tidak cocok");
                }
                else et_konfirmSandi.setError(null);
            }
        });

        btnDaftar.setOnClickListener(this);
        tv_masuk.setOnClickListener(this);
    }

    public void onStart() {
        super.onStart();

        EditText txtDate = (EditText) findViewById(R.id.et_tanggalLahir_register);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_daftar_register:
                if (cekValidasi() && cekEmailPattern()){
                    String email = et_email.getText().toString();
                    String nama = et_nama.getText().toString();
                    String date = et_tanggalLahir.getText().toString();
                    String password = et_sandi.getText().toString();
                    Date dateOfBirth;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        dateOfBirth = format.parse(date);
                        signupUserEmail(nama, email, dateOfBirth, password);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.tv_masuk_register:
                startActivity(new Intent(RegisterActivity.this, NavigationActivity.class));
        }
    }

    @Override
    public boolean cekValidasi() {

        if (et_nama.getText().toString().isEmpty() || et_tanggalLahir.getText().toString().isEmpty() || et_sandi.getText().length()<6){
            if (et_nama.getText().toString().isEmpty()){
                et_nama.setError("Isi nama anda telebih dahulu");
            } else if (et_tanggalLahir.getText().toString().isEmpty()){
                et_tanggalLahir.setError("Isikan tanggal lahir dahulu");
            } else if (et_sandi.length()<6){
                et_sandi.setError("Panjang password minimal 6 karakter");
            }
            return false;
        }
        else return true;
    }

    @Override
    public boolean cekEmailPattern() {
        if (Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()){
            et_email.setError(null);
            return true;
        }
        else {
            et_email.setError("Masukkan format email yang benar");
            return false;
        }
    }

    @Override
    public void signupUserEmail(String name, String email, Date dateOfBirth, String password) {
//        mAuth.createUserWithEmailAndPassword(email, password)
//        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(RegisterActivity.this, "Selamat, " + email + " sudah terdaftar", Toast.LENGTH_LONG).show();
//                }
//                else Log.v("gagalRegister", " gagal bung" + task.getException());
//            }
//        });

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseRegister> registerUser = apiRequest.sendRegister(name, email, dateOfBirth, password);
        registerUser.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                Toast.makeText(RegisterActivity.this, "Masuk onResponse", Toast.LENGTH_LONG).show();

                Toast.makeText(RegisterActivity.this, "Sealamat datang : " + response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Maaf : " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
}

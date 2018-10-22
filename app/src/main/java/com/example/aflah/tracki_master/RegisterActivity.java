package com.example.aflah.tracki_master;

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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements IRegister, View.OnClickListener {

    Button btnDaftar;
    EditText et_nama, et_email, et_sandi, et_konfirmSandi;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_nama = (EditText) findViewById(R.id.et_nama_register);
        et_email = (EditText) findViewById(R.id.et_email_register);
        et_sandi = (EditText) findViewById(R.id.et_sandi_register); //email harus minimal 6 karakter
        et_konfirmSandi = (EditText) findViewById(R.id.et_konfirmSandi_register);
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
                if (!s.toString().equals(password))
                    et_konfirmSandi.setError("Password tidak cocok");
                else et_konfirmSandi.setError(null);
            }
        });
        btnDaftar.setOnClickListener(this);
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
                if (cekValidasi()){
                    signupUserEmail(et_email.getText().toString(), et_konfirmSandi.getText().toString());
                }
                break;
        }

    }

    @Override
    public void signupUserEmail(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Selamat, " + email + " sudah terdaftar", Toast.LENGTH_LONG).show();
                }
                else Log.v("gagalRegister", " gagal bung" + task.getException());
            }
        });
    }

    public boolean cekValidasi(){
        if (Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()){
            et_email.setError(null);
            return true;
        }
        else{
            et_email.setError("Masukkan format email yang benar");
            return false;
        }
    }

//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            snackbar = Snackbar.make(activity_sign_up_layout, "Register berhasil ", Snackbar.LENGTH_LONG);
//                            snackbar.show();
//                        }
//                    }
//                });
//
//    if (Patterns.EMAIL_ADDRESS.matcher(et_email_register.getText().toString()).matches())
//            et_email_register.setError(null);
//                else
//                        et_email_register.setError("Masukkan format email yang benar");

}

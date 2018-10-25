package com.example.aflah.tracki_master.Auth;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.NavigationActivity;
import com.example.aflah.tracki_master.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity implements View.OnClickListener, ILogin {

    Button btnLogin;
    TextView tvDaftar;
    EditText etEmail, etPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        tvDaftar = (TextView) findViewById(R.id.tv_daftar_login);
        btnLogin = (Button) findViewById(R.id.btn_masuk_login);
        etEmail = (EditText) findViewById(R.id.et_email_login);
        etPassword = (EditText) findViewById(R.id.et_sandi_login);

        tvDaftar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_daftar_login :
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_masuk_login:
                loginEmailPassword(etEmail.getText().toString(), etPassword.getText().toString());
                break;
        }
    }

    @Override
    public void loginEmailPassword(String email, String password) {


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
                        else Toast.makeText(LoginActivity.this, "Maaf, " + task.getException(), Toast.LENGTH_LONG).show();
                    }
                });
    }

}

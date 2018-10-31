package com.example.aflah.tracki_master.Auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.aflah.tracki_master.Model.Response.ResponseLogin;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.NavigationActivity;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends Activity implements View.OnClickListener, ILogin {

    Button btnLogin, btnMasukTamu;
    TextView tvDaftar;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handleIntent(getIntent());

        tvDaftar = (TextView) findViewById(R.id.tv_daftar_login);
        btnLogin = (Button) findViewById(R.id.btn_masuk_login);
        btnMasukTamu = (Button) findViewById(R.id.btn_masukTamu_login);
        etEmail = (EditText) findViewById(R.id.et_email_login);
        etPassword = (EditText) findViewById(R.id.et_sandi_login);

        tvDaftar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnMasukTamu.setOnClickListener(this);
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_daftar_login :
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_masuk_login:

                loginEmailPassword(etEmail.getText().toString(), etPassword.getText().toString());
                finish();
                break;
            case R.id.btn_masukTamu_login:
                loginSebagaiTamu();
                startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void loginEmailPassword(String email, String password) {
        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseLogin> loginUser = apiRequest.sendLogin(email, password);
        loginUser.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                UserLogin userLogin = response.body().getUserLogin();
                String token = response.body().getAccessToken();
                Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);

                Gson gson = new Gson();
                String json = gson.toJson(userLogin);
                SharedPreferences.Editor editor = getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                editor.putString("tokenLogin", token);
                editor.putString("userLogin", json);
                editor.apply();
                editor.commit();


                startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "onFailure :  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void loginSebagaiTamu() {
        SharedPreferences.Editor editor = getSharedPreferences("login", Context.MODE_PRIVATE).edit();
        editor.putString("tokenLogin", "");
        editor.putString("userLogin", "");
        editor.apply();
        editor.commit();

    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null){
            String recipeId = appLinkData.getLastPathSegment();
            Uri appData = Uri.parse("content://com.recipe_app/recipe/").buildUpon()
                    .appendPath(recipeId).build();
            Toast.makeText(LoginActivity.this, "appData : " + appData, Toast.LENGTH_LONG).show();
        }
    }

}

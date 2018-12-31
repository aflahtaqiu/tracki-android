package com.example.aflah.tracki_master.Auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.View.ForgotPasswordActivity;
import com.example.aflah.tracki_master.Model.Response.ResponseLogin;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.NavigationActivity;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;

import com.google.gson.Gson;

import java.io.IOException;

import me.omidh.ripplevalidatoredittext.RVEValidatorFactory;
import me.omidh.ripplevalidatoredittext.RVEValidatorType;
import me.omidh.ripplevalidatoredittext.RippleValidatorEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends Activity implements View.OnClickListener, ILogin {

    Button btnLogin, btnMasukTamu;
    TextView tvDaftar, tvLupaPassword;
    RippleValidatorEditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handleIntent(getIntent());

        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String userToken = sharedPreferences.getString("tokenLogin", "");
        if (!userToken.isEmpty()){
            startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
            finish();
        }

        tvDaftar = (TextView) findViewById(R.id.tv_daftar_login);
        tvLupaPassword = (TextView) findViewById(R.id.tv_lupaPassword_login);
        btnLogin = (Button) findViewById(R.id.btn_masuk_login);
        btnMasukTamu = (Button) findViewById(R.id.btn_masukTamu_login);
        etEmail = (RippleValidatorEditText) findViewById(R.id.et_email_login);
        etPassword = (RippleValidatorEditText) findViewById(R.id.et_sandi_login);

        etEmail.addValidator(
                RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Email harus diisi", null),
                RVEValidatorFactory.getValidator(RVEValidatorType.EMAIL, "ex: john@doe.com", null)
        );
        etPassword.addValidator(
                RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Anda harus mengisi password", null)
        );

        tvDaftar.setOnClickListener(this);
        tvLupaPassword.setOnClickListener(this);
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
                finish();
                break;
            case R.id.btn_masuk_login:
                if (cekValidasi()){
                    loginEmailPassword(etEmail.getText().toString(), etPassword.getText().toString());
                }
                break;
            case R.id.btn_masukTamu_login:
                loginSebagaiTamu();
                startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
                finish();
                break;
            case R.id.tv_lupaPassword_login:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
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

                try{
                    String message = response.body().getMessage();
                    UserLogin userLogin = response.body().getUserLogin();
                    String token = response.body().getAccessToken();
                    Gson gson = new Gson();
                    String json = gson.toJson(userLogin);
                    SharedPreferences.Editor editor = getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                    editor.putString("tokenLogin","Bearer "+ token);
                    editor.putString("userLogin", json);
                    editor.apply();
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
                    finish();
                }catch (Exception e){
                    String message = null;
                    try {
                        message = response.errorBody().string();
                        if (message.equals("{\"message\":\"email\"}")){
                            if (etEmail.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EQUAL, "Email Anda belum terdaftar", etEmail.getText().toString() + " "), true)){
                                etEmail.getEditText().setText("");

                            }
                        } else if(message.equals("{\"message\":\"password\"}")){
                            if (etPassword.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EQUAL, "Password Anda salah", etPassword.getText().toString()+ " "), true)
                                    ){
                                etPassword.getEditText().setText("");
                            }
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
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

    @Override
    public boolean cekValidasi() {
        if (etEmail.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Email harus diisi", null),true) &&
                etEmail.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMAIL, "ex: john@doe.com", null),true) &&
                etPassword.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Anda harus mengisi password", null),true)){
            return true;
        } else return false;
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

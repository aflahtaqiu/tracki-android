package com.example.aflah.tracki_master.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.Contract.LoginContract;
import com.example.aflah.tracki_master.Presenter.LoginPresenter;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.NavigationActivity;
import com.example.aflah.tracki_master.R;

import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.omidh.ripplevalidatoredittext.RVEValidatorFactory;
import me.omidh.ripplevalidatoredittext.RVEValidatorType;
import me.omidh.ripplevalidatoredittext.RippleValidatorEditText;

public class LoginActivity extends Activity implements View.OnClickListener, LoginContract.view {

    Button btnLogin, btnMasukTamu;
    TextView tvDaftar, tvLupaPassword;
    RippleValidatorEditText etEmail, etPassword;
    SweetAlertDialog sweetAlertDialog;

    LoginPresenter presenter = new LoginPresenter(Injection.provideUserRepository(), this);

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
                presenter.loginUser(etEmail.getText().toString(), etPassword.getText().toString());

                break;
            case R.id.btn_masukTamu_login:
                presenter.loginAsGuest();
                startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
                finish();
                break;
            case R.id.tv_lupaPassword_login:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;
        }
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

    @Override
    public void showProgress() {
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#B40037"));
        sweetAlertDialog.getProgressHelper().setRimColor(Color.parseColor("#B40037"));
        sweetAlertDialog.setTitleText("Loading");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.show();
    }

    @Override
    public void hideProgress() {
        sweetAlertDialog.dismiss();
    }

    @Override
    public void showError(String errMsg) {
        Toast.makeText(this, "Ada error : "+ errMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFailure(int code, String pesan) {
        if (code == 1){
              etEmail.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EQUAL,"Email Anda belum terdaftar", etEmail.getText().toString()+"1"), false);
              etEmail.getEditText().setText("");
              etPassword.getEditText().setText("");
        } else if (code == 2){
              etPassword.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EQUAL, "Password Anda salah", etPassword.getText().toString()+ " "), false);
              etPassword.getEditText().setText("");
        }
        sweetAlertDialog
                .setTitleText("Login User")
                .setContentText(pesan)
                .setConfirmText("OK")
                .showCancelButton(false)
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.show();
    }

    @Override
    public void changeActivity(UserLogin userLogin, String token) {
        Gson gson = new Gson();
        String json = gson.toJson(userLogin);
        SharedPreferences.Editor editor = getSharedPreferences("login", Context.MODE_PRIVATE).edit();
        editor.putString("tokenLogin","Bearer "+ token);
        editor.putString("userLogin", json);
        editor.apply();
        editor.commit();
        startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
        finish();
    }

    @Override
    public void loginAsGues() {
        SharedPreferences.Editor editor = getSharedPreferences("login", Context.MODE_PRIVATE).edit();
        editor.putString("tokenLogin", "");
        editor.putString("userLogin", "");
        editor.apply();
        editor.commit();
    }

    @Override
    public boolean checkInput() {
        if (etEmail.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Email harus diisi", null), false) &&
                etEmail.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMAIL, "ex: john@doe.com", null),false) &&
                etPassword.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Anda harus mengisi password", null),false)){
            return true;
        } else return false;
    }
}

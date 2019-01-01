package com.example.aflah.tracki_master.View;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aflah.tracki_master.Contract.RegisterContract;
import com.example.aflah.tracki_master.Presenter.RegisterPresenter;
import com.example.aflah.tracki_master.DateDialog;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.NavigationActivity;
import com.example.aflah.tracki_master.R;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.omidh.ripplevalidatoredittext.RVEValidatorFactory;
import me.omidh.ripplevalidatoredittext.RVEValidatorType;
import me.omidh.ripplevalidatoredittext.RippleValidatorEditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RegisterContract.view {

    Button btnDaftar;
    TextView tv_masuk;
    RippleValidatorEditText etNama, etEmail, etSandi, etSandiKonfirmasi, etTanggalLahir;
    SweetAlertDialog sweetAlertDialog;

    private RegisterPresenter presenter = new RegisterPresenter(Injection.provideUserRepository(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initEditText();

        btnDaftar.setOnClickListener(this);
        tv_masuk.setOnClickListener(this);
    }

    private void initViews() {
        etNama = (RippleValidatorEditText) findViewById(R.id.et_nama_register);
        etEmail = (RippleValidatorEditText) findViewById(R.id.et_email_register);
        etSandi = (RippleValidatorEditText) findViewById(R.id.et_sandi_register);
        etSandiKonfirmasi = (RippleValidatorEditText) findViewById(R.id.et_konfirmSandi_register);
        etTanggalLahir = (RippleValidatorEditText) findViewById(R.id.et_tanggalLahir_register);
        tv_masuk = (TextView) findViewById(R.id.tv_masuk_register);
        btnDaftar = (Button) findViewById(R.id.btn_daftar_register);
    }

    private void initEditText() {
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
                String nama = etNama.getText().toString();
                String email = etEmail.getText().toString();
                String password = etSandi.getText().toString();
                String konfirmasiPassword = etSandiKonfirmasi.getText().toString();
                String date = etTanggalLahir.getText().toString();
                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date dateOfBirth = inputFormat.parse(date);
                    presenter.registerUser(nama, email, dateOfBirth, password, konfirmasiPassword);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_masuk_register:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
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
    public void showSuccess(String pesan) {
        sweetAlertDialog
                .setTitleText("Register User")
                .setContentText(pesan)
                .setConfirmText("OK")
                .showCancelButton(false)
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.show();
    }

    @Override
    public void showFail(String pesan) {
        sweetAlertDialog
                .setTitleText("Register User")
                .setContentText(pesan)
                .setConfirmText("OK")
                .showCancelButton(false)
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.show();
        etEmail.getEditText().setText("");
    }

    @Override
    public boolean checkInput() {
        if (etNama.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY,"Anda belum mengisi nama Anda",null),false) &&
                etEmail.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Anda belum mengisi email Anda", null),false) &&
                etSandi.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Anda belum mengisi sandi Anda", null),false) &&
                etSandiKonfirmasi.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Anda belum mengisi konfirmasi sandi Anda", null),false) &&
                etTanggalLahir.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Isi tanggal Lahir Anda", null),false)){
            if (etEmail.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EMAIL, "ex : john@doe.com", null),false) &&
                    etSandi.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.MIN_LENGTH, "Password minimal 8 karakter", 8), false) &&
                    etSandiKonfirmasi.validateWith(RVEValidatorFactory.getValidator(RVEValidatorType.EQUAL, "Tidak cocok dengan sandi Anda", etSandi.getText().toString()),false)){
                return true;
            }
            else return false;
        }
        else return false;
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
        startActivity(new Intent(RegisterActivity.this, NavigationActivity.class));
        finish();
    }
}

package com.example.aflah.tracki_master.View;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aflah.tracki_master.Contract.ForgotPasswordContract;
import com.example.aflah.tracki_master.Presenter.ForgotPasswordPresenter;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordContract.view {

    EditText editTextEmail;
    Button btnKirim;
    SweetAlertDialog sweetAlertDialog;

    private ForgotPasswordPresenter presenter = new ForgotPasswordPresenter(Injection.provideUserRepository(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = (EditText) findViewById(R.id.forget_input_email);
        btnKirim = (Button) findViewById(R.id.buttonKirim_lupaEmail);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendEmail(editTextEmail.getText().toString());
            }
        });
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
                .setTitleText("Reset Password")
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
                .setTitleText("Reset Password")
                .setContentText(pesan)
                .setConfirmText("OK")
                .showCancelButton(false)
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.show();
    }

    @Override
    public void showError(String errMsg) {
        Toast.makeText(this, "Ada error : " + errMsg, Toast.LENGTH_LONG).show();
    }
}

package com.example.aflah.tracki_master.View;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aflah.tracki_master.Contract.EditProfilContract;
import com.example.aflah.tracki_master.Presenter.EditProfilePresenter;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.User;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.R;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EditProfilActivity extends Activity implements View.OnClickListener, EditProfilContract.view {

    ImageView correct, close;
    EditText etNama, etTanggalLahir;
    SharedPreferences sharedPreferences;
    UserLogin userLogin;
    String userToken;
    SweetAlertDialog sweetAlertDialogProgress;

    private EditProfilePresenter presenter = new EditProfilePresenter(Injection.provideUserRepository(), this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userLogin", "");
        userLogin = gson.fromJson(json, UserLogin.class);
        userToken = sharedPreferences.getString("tokenLogin", "");

        initViews();
        setCurrentValues();

        correct.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    private void setCurrentValues() {
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
    }

    private void initViews() {
        correct = (ImageView) findViewById(R.id.iv_correctEditProfil);
        close = (ImageView) findViewById(R.id.iv_closeEditProfil);
        etNama = (EditText) findViewById(R.id.et_nama_editProfil);
        etTanggalLahir = (EditText) findViewById(R.id.et_tanggalLahir_editProfil);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_correctEditProfil:
                String date = etTanggalLahir.getText().toString();
                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date dateOfBirth = inputFormat.parse(date);
                    presenter.updateProfile(userLogin.getId(), userToken, etNama.getText().toString(), dateOfBirth);
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
        setDatePicker();
    }

    private void setDatePicker() {
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

    @Override
    public void showProgress() {
        sweetAlertDialogProgress = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialogProgress.getProgressHelper().setBarColor(Color.parseColor("#B40037"));
        sweetAlertDialogProgress.getProgressHelper().setRimColor(Color.parseColor("#B40037"));
        sweetAlertDialogProgress.setTitleText("Loading");
        sweetAlertDialogProgress.setCancelable(false);
        sweetAlertDialogProgress.setCanceledOnTouchOutside(true);
        sweetAlertDialogProgress.show();
    }

    @Override
    public void hideProgress() {
        sweetAlertDialogProgress.dismiss();
    }

    @Override
    public void showError(String errMsg) {
        Toast.makeText(this, "Ada error : " + errMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateUI(User userBaru) {
        Gson gson = new Gson();
        String json = gson.toJson(userBaru);
        SharedPreferences.Editor editor = getSharedPreferences("login", Context.MODE_PRIVATE).edit();
        editor.putString("userLogin", json);
        editor.apply();
        editor.commit();

        Intent intent = new Intent(getApplicationContext(),NavigationActivity.class);
        intent.putExtra("LOC",R.id.navigation_account);
        startActivity(intent);
    }
}

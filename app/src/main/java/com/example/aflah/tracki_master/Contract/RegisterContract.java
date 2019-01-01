package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.UserLogin;

import java.util.Date;

public interface RegisterContract {

    interface presenter {
        void registerUser(String nama, String email, Date dateOfBirth, String password, String konfirmPassword);

    }

    interface view {
        void showProgress();
        void hideProgress();
        void showSuccess(String pesan);
        void showFail(String pesan);
        boolean checkInput();
        void changeActivity(UserLogin userLogin, String token);
    }
}

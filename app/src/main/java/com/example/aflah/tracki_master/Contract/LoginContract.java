package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.UserLogin;

public interface LoginContract {

    interface presenter {
        void loginUser(String email, String password);
        void loginAsGuest();
    }

    interface view {
        void showProgress();
        void hideProgress();
        void showError(String errMsg);
        void showFailure(int kode, String pesan);
        void changeActivity(UserLogin userLogin, String token);
        void loginAsGues();
        boolean checkInput();
    }
}

package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.User;

import java.util.Date;

public interface EditProfilContract {

    interface presenter {
        void updateProfile(int idUser, String userToken, String namaUser, Date dateOfBirthUser);
        void getUserById(int idUser);
    }

    interface view {
        void showProgress();
        void hideProgress();
        void showError(String errMsg);
        void updateUI(User userBaru);
    }
}

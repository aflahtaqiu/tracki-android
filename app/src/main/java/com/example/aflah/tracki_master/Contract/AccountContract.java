package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface AccountContract {

    interface presenter {
        void getSavedUnusedPromo(int idUse);
        void logoutUser();
        void sendLogout();
        void updatePhotoUser(int idUser, String userToken, MultipartBody.Part multipartBody, RequestBody requestMethod);
    }

    interface view {
        void cleanSharedPreferences();
        void showProgress();
        void hideProgress();
        void showConfirmationDialog();
        void showFailure(String errMsg);
        void noSavedPromo();
        void showListPromo(List<Promotion> promotionList);
        void showFotoUser(User user);
        void updateSharedPreferences(User user);
    }
}

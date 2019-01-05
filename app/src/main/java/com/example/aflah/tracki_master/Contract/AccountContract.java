package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.Promotion;

import java.util.List;

public interface AccountContract {

    interface presenter {
        void getSavedUnusedPromo(int idUse);
        void logoutUser();
        void sendLogout();
    }

    interface view {
        void cleanSharedPreferences();
        void showProgress();
        void hideProgress();
        void showConfirmationDialog();
        void showFailure(String errMsg);
        void noSavedPromo();
        void showListPromo(List<Promotion> promotionList);
    }
}

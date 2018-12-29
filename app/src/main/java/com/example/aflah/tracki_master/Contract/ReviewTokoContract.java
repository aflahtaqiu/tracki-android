package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.Store;

public interface ReviewTokoContract {

    interface view {
        void showProgress();
        void hideProgress();
        void showListReview(Store store);
        void showError(String error);
    }

    interface presenter {
        void getReview(int idToko);
        void sendReview(String userToken, int idToko, double ratingToko, String komentarToko);
    }
}

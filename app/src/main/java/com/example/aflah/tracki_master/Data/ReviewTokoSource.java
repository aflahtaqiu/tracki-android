package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Model.Store;

public interface ReviewTokoSource {

    void getReview(int idStore, GetReviewByStoreCallback callback);
    void sendReview(String userToken, int idToko, double ratingToko, String komentarToko, SendReviewByStoreCallback callback);

    interface GetReviewByStoreCallback {

        void onSuccess(Store store);

        void onFailure(String errMsg);
    }

    interface SendReviewByStoreCallback {

        void onSuccess();

        void onFailure(String errMsg);
    }
}

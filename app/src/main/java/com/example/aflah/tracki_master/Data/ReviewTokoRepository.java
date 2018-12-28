package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Data.remote.ReviewRemoteDataSource;
import com.example.aflah.tracki_master.Model.Store;

public class ReviewTokoRepository implements ReviewTokoSource {

    private ReviewRemoteDataSource reviewRemoteDataSource;

    public ReviewTokoRepository(ReviewRemoteDataSource reviewRemoteDataSource) {
        this.reviewRemoteDataSource = reviewRemoteDataSource;
    }

    @Override
    public void getReview(int idStore, GetReviewByStoreCallback callback) {
        reviewRemoteDataSource.getReview(idStore, new GetReviewByStoreCallback() {
            @Override
            public void onSuccess(Store store) {
                callback.onSuccess(store);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }

    @Override
    public void sendReview(String userToken, int idToko, double ratingToko, String komentarToko, SendReviewByStoreCallback callback) {
        reviewRemoteDataSource.sendReview(userToken, idToko, ratingToko, komentarToko, new SendReviewByStoreCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onFailure(String errMsg) {
                onFailure(errMsg);
            }
        });
    }
}

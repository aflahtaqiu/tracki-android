package com.example.aflah.tracki_master.Presenter;

import com.example.aflah.tracki_master.Contract.ReviewTokoContract;
import com.example.aflah.tracki_master.Data.ReviewTokoRepository;
import com.example.aflah.tracki_master.Data.ReviewTokoSource;
import com.example.aflah.tracki_master.Model.Store;

public class ReviewTokoPresenter implements ReviewTokoContract.presenter {

    private ReviewTokoRepository reviewTokoRepository;
    private ReviewTokoContract.view view;

    public ReviewTokoPresenter(ReviewTokoRepository reviewTokoRepository, ReviewTokoContract.view view) {
        this.reviewTokoRepository = reviewTokoRepository;
        this.view = view;
    }

    @Override
    public void getReview(int idToko) {
        view.showProgress();
        reviewTokoRepository.getReview(idToko, new ReviewTokoSource.GetReviewByStoreCallback() {
            @Override
            public void onSuccess(Store store) {
                view.hideProgress();
                view.showListReview(store);
            }

            @Override
            public void onFailure(String errMsg) {
                view.hideProgress();
                view.showError(errMsg);
            }
        });
    }

    @Override
    public void sendReview(String userToken, int idToko, double ratingToko, String komentarToko) {
        reviewTokoRepository.sendReview(userToken, idToko, ratingToko, komentarToko, new ReviewTokoSource.SendReviewByStoreCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(String errMsg) {
                view.showError(errMsg);
            }
        });
    }
}

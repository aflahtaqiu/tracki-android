package com.example.aflah.tracki_master.Presenter;

import com.example.aflah.tracki_master.Contract.AccountContract;
import com.example.aflah.tracki_master.Data.UserRepository;
import com.example.aflah.tracki_master.Data.UserSource;
import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AccountPresenter implements AccountContract.presenter {

    private UserRepository userRepository;
    private AccountContract.view view;

    public AccountPresenter(UserRepository userRepository, AccountContract.view view) {
        this.userRepository = userRepository;
        this.view = view;
    }

    @Override
    public void getSavedUnusedPromo(int idUser) {
        view.showProgress();
        userRepository.getSavedPromotion(idUser, new UserSource.GetSavedPromoCallback() {
            @Override
            public void onSuccess(List<Promotion> promotionList) {
                view.hideProgress();
                if (promotionList.size() == 0)
                    view.noSavedPromo();
                else view.showListPromo(promotionList);
            }

            @Override
            public void onFailure(String errMsg) {
                view.hideProgress();
                view.showFailure(errMsg);
            }
        });
    }


    @Override
    public void logoutUser() {
        view.cleanSharedPreferences();
        view.showConfirmationDialog();
    }

    @Override
    public void sendLogout() {
        userRepository.logoutUser(new UserSource.LogoutCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(String errMsg) {
                view.showFailure(errMsg);
            }
        });
    }

    @Override
    public void updatePhotoUser(int idUser, String userToken, MultipartBody.Part multipartBody, RequestBody requestMethod) {
        view.showProgress();
        userRepository.updateFotoUser(idUser, userToken, multipartBody, requestMethod, new UserSource.UpdateFotoCallback() {
            @Override
            public void onSuccess(User user) {
                view.hideProgress();
                view.updateSharedPreferences(user);
                view.showFotoUser(user);
            }

            @Override
            public void onFailure(String errMsg) {
                view.hideProgress();
                view.showFailure(errMsg);
            }
        });
    }
}

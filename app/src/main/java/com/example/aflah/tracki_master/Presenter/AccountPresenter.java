package com.example.aflah.tracki_master.Presenter;

import com.example.aflah.tracki_master.Contract.AccountContract;
import com.example.aflah.tracki_master.Data.UserRepository;
import com.example.aflah.tracki_master.Data.UserSource;
import com.example.aflah.tracki_master.Model.Promotion;

import java.util.List;

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

    }
}

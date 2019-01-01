package com.example.aflah.tracki_master.Presenter;

import com.example.aflah.tracki_master.Contract.ForgotPasswordContract;
import com.example.aflah.tracki_master.Data.UserRepository;
import com.example.aflah.tracki_master.Data.UserSource;

public class ForgotPasswordPresenter implements ForgotPasswordContract.presenter {

    private UserRepository userRepository;
    private ForgotPasswordContract.view view;

    public ForgotPasswordPresenter(UserRepository userRepository, ForgotPasswordContract.view view) {
        this.userRepository = userRepository;
        this.view = view;
    }

    @Override
    public void sendEmail(String email) {
        view.showProgress();
        userRepository.resetUserPassword(email, new UserSource.ResetUserPasswordCallback() {

            @Override
            public void onSuccess(String message, int code) {
                view.hideProgress();
                if (code == 200) view.showSuccess(message);
                else view.showFail(message);
            }

            @Override
            public void onFailure(String errMsg) {
                view.showError(errMsg);
            }
        });
    }
}

package com.example.aflah.tracki_master.Presenter;

import com.example.aflah.tracki_master.Contract.RegisterContract;
import com.example.aflah.tracki_master.Data.UserRepository;
import com.example.aflah.tracki_master.Data.UserSource;
import com.example.aflah.tracki_master.Model.UserLogin;

import java.util.Date;

public class RegisterPresenter implements RegisterContract.presenter {

    private UserRepository userRepository;
    private RegisterContract.view view;

    public RegisterPresenter(UserRepository userRepository, RegisterContract.view view) {
        this.userRepository = userRepository;
        this.view = view;
    }

    @Override
    public void registerUser(String nama, String email, Date dateOfBirth, String password, String konfirmPassword) {
        if (view.checkInput() == true){
            view.showProgress();
            userRepository.registerUser(nama, email, dateOfBirth, password, konfirmPassword, new UserSource.RegisterUserCallback() {
                @Override
                public void onSuccess(UserLogin userLogin, String token, String pesan) {
                    view.hideProgress();
                    view.showSuccess(pesan);
                    view.changeActivity(userLogin, token);
                }

                @Override
                public void onFailure(String errMsg) {
                    view.hideProgress();
                    view.showFail(errMsg);
                }
            });
        }
    }
}

package com.example.aflah.tracki_master.Presenter;

import com.example.aflah.tracki_master.Contract.LoginContract;
import com.example.aflah.tracki_master.Data.UserRepository;
import com.example.aflah.tracki_master.Data.UserSource;
import com.example.aflah.tracki_master.Model.UserLogin;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter implements LoginContract.presenter {

    private UserRepository userRepository;
    private LoginContract.view view;

    public LoginPresenter(UserRepository userRepository, LoginContract.view view) {
        this.userRepository = userRepository;
        this.view = view;
    }

    @Override
    public void loginUser(String email, String password) {
        if (view.checkInput()== true){
            view.showProgress();
            userRepository.loginUser(email, password, new UserSource.LoginUserCallback() {
                @Override
                public void onSuccess(UserLogin userLogin, String token, String pesan, int code) {
                    view.hideProgress();
                    if (code == 201){
                        view.changeActivity(userLogin, token);
                    } else{
                        try {
                            JSONObject jsonObject = new JSONObject(pesan);
                            if (jsonObject.getString("message").equals("email"))
                                view.showFailure(1, "Maaf email Anda belum terdaftar");
                            else view.showFailure(2, "Maaf password Anda salah");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(String errMsg) {
                    view.hideProgress();
                    view.showError(errMsg);
                }
            });
        }
    }

    @Override
    public void loginAsGuest() {
        view.loginAsGues();
    }
}

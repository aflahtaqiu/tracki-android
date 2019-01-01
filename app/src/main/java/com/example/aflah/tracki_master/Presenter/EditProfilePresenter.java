package com.example.aflah.tracki_master.Presenter;

import android.app.Activity;

import com.example.aflah.tracki_master.Contract.EditProfilContract;
import com.example.aflah.tracki_master.Data.UserRepository;
import com.example.aflah.tracki_master.Data.UserSource;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.User;

import java.util.Date;

public class EditProfilePresenter extends Activity implements EditProfilContract.presenter {

    private UserRepository userRepository;
    private EditProfilContract.view view;

    public EditProfilePresenter(UserRepository userRepository, EditProfilContract.view view) {
        this.userRepository = userRepository;
        this.view = view;
    }

    @Override
    public void updateProfile(int idUser, String userToken, String namaUser, Date dateOfBirthUser) {
        view.showProgress();
        userRepository.updateProfile(idUser, userToken, namaUser, dateOfBirthUser, new UserSource.UpdateProfileCallback() {
            @Override
            public void onSuccess(ResponseUserById responseUserById) {
                getUserById(idUser);
            }

            @Override
            public void onFailure(String errMsg) {
                view.showError(errMsg);
            }
        });
    }

    @Override
    public void getUserById(int idUser) {
        userRepository.getUserById(idUser, new UserSource.GetUserByIdCallback() {
            @Override
            public void onSuccess(User user) {
                view.updateUI(user);
            }

            @Override
            public void onFailure(String errMsg) {
                view.hideProgress();
                view.showError(errMsg);
            }
        });
    }
}

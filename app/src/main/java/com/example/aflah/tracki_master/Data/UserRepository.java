package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Data.remote.UserRemoteDataSource;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.User;
import com.example.aflah.tracki_master.Model.UserLogin;

import java.util.Date;

public class UserRepository implements UserSource {

    private UserRemoteDataSource userRemoteDataSource;

    public UserRepository(UserRemoteDataSource userRemoteDataSource) {
        this.userRemoteDataSource = userRemoteDataSource;
    }

    @Override
    public void updateProfile(int idUser, String userToken, String namaUser, Date dateOfBirthUser, UpdateProfileCallback callback) {
        userRemoteDataSource.updateProfile(idUser, userToken, namaUser, dateOfBirthUser, new UpdateProfileCallback() {
            @Override
            public void onSuccess(ResponseUserById responseUserById) {
                callback.onSuccess(responseUserById);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }

    @Override
    public void getUserById(int idUser, GetUserByIdCallback callback) {
        userRemoteDataSource.getUserById(idUser, new GetUserByIdCallback() {
            @Override
            public void onSuccess(User user) {
                callback.onSuccess(user);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }

    @Override
    public void resetUserPassword(String email, ResetUserPasswordCallback callback) {
        userRemoteDataSource.resetUserPassword(email, new ResetUserPasswordCallback() {
            @Override
            public void onSuccess(String message, int code) {
                callback.onSuccess(message, code);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }

    @Override
    public void registerUser(String nama, String email, Date dateOfBirth, String password, String confirmPassword, RegisterUserCallback callback) {
        userRemoteDataSource.registerUser(nama, email, dateOfBirth, password, confirmPassword, new RegisterUserCallback() {
            @Override
            public void onSuccess(UserLogin userLogin, String token, String pesan) {
                callback.onSuccess(userLogin, token, pesan);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }

    @Override
    public void loginUser(String email, String password, LoginUserCallback callback) {
        userRemoteDataSource.loginUser(email, password, new LoginUserCallback() {
            @Override
            public void onSuccess(UserLogin userLogin, String token, String pesan) {
                callback.onSuccess(userLogin, token, pesan);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }
}

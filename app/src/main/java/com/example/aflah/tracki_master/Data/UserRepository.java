package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Data.remote.UserRemoteDataSource;
import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.User;
import com.example.aflah.tracki_master.Model.UserLogin;

import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
            public void onSuccess(UserLogin userLogin, String token, String pesan, int code) {
                callback.onSuccess(userLogin, token, pesan, code);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }

    @Override
    public void logoutUser(LogoutCallback callback) {
        userRemoteDataSource.logoutUser(new LogoutCallback() {

            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }

    @Override
    public void getSavedPromotion(int idUser, GetSavedPromoCallback callback) {
        userRemoteDataSource.getSavedPromotion(idUser, new GetSavedPromoCallback() {
            @Override
            public void onSuccess(List<Promotion> promotionList) {
                callback.onSuccess(promotionList);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }

    @Override
    public void updateFotoUser(int idUser, String userToken, MultipartBody.Part multipartBody, RequestBody requestMethod, UpdateFotoCallback callback) {
        userRemoteDataSource.updateFotoUser(idUser, userToken, multipartBody, requestMethod, new UpdateFotoCallback() {
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
}

package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.User;
import com.example.aflah.tracki_master.Model.UserLogin;

import java.util.Date;
import java.util.List;

public interface UserSource {

    void updateProfile(int idUser, String userToken, String namaUser, Date dateOfBirthUser, UpdateProfileCallback callback);
    void getUserById(int idUser, GetUserByIdCallback callback);
    void resetUserPassword(String email, ResetUserPasswordCallback callback);
    void registerUser(String nama, String email, Date dateOfBirth, String password, String confirmPassword, RegisterUserCallback callback);
    void loginUser(String email, String password, LoginUserCallback callback);
    void logoutUser(LogoutCallback callback);
    void getSavedPromotion(int idUser, GetSavedPromoCallback callback);

    interface UpdateProfileCallback {
        void onSuccess(ResponseUserById responseUserById);
        void onFailure(String errMsg);
    }

    interface GetUserByIdCallback {
        void onSuccess(User user);
        void onFailure(String errMsg);
    }

    interface ResetUserPasswordCallback {
        void onSuccess(String message, int code);
        void onFailure(String errMsg);
    }

    interface RegisterUserCallback {
        void onSuccess(UserLogin userLogin, String token, String pesan);
        void onFailure(String errMsg);
    }

    interface LoginUserCallback {
        void onSuccess(UserLogin userLogin, String token, String pesan, int code);
        void onFailure(String errMsg);
    }

    interface LogoutCallback {
        void onSuccess();
        void onFailure(String errMsg);
    }

    interface GetSavedPromoCallback {
        void onSuccess(List<Promotion> promotionList);
        void onFailure(String errMsg);
    }
}

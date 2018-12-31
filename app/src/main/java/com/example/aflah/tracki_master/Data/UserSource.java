package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.User;

import java.util.Date;

public interface UserSource {

    void updateProfile(int idUser, String userToken, String namaUser, Date dateOfBirthUser, UpdateProfileCallback callback);
    void getUserById(int idUser, GetUserByIdCallback callback);
    void resetUserPassword(String email, ResetUserPasswordCallback callback);

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
}
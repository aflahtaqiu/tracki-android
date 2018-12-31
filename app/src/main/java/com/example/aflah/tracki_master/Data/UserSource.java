package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.User;
import com.example.aflah.tracki_master.Model.UserLogin;

import java.util.Date;

public interface UserSource {

    void updateProfile(int idUser, String userToken, String namaUser, Date dateOfBirthUser, UpdateProfileCallback callback);
    void getUserById(int idUser, GetUserByIdCallback callback);
    void resetUserPassword(String email, ResetUserPasswordCallback callback);
    void registerUser(String nama, String email, Date dateOfBirth, String password, String confirmPassword, RegisterUserCallback callback);

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
}

package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Data.remote.UserRemoteDataSource;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.User;

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
}

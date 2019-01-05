package com.example.aflah.tracki_master.Data.remote;

import com.example.aflah.tracki_master.Data.UserSource;
import com.example.aflah.tracki_master.Data.remote.API.ApiClient;
import com.example.aflah.tracki_master.Data.remote.API.ApiInterface;
import com.example.aflah.tracki_master.Model.Response.ResponseForgotPassword;
import com.example.aflah.tracki_master.Model.Response.ResponseLogin;
import com.example.aflah.tracki_master.Model.Response.ResponseLogout;
import com.example.aflah.tracki_master.Model.Response.ResponseRegister;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRemoteDataSource implements UserSource {

    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    public void updateProfile(int idUser, String userToken, String namaUser, Date dateOfBirthUser, UpdateProfileCallback callback) {
        Call<ResponseUserById> call = apiInterface.updateProfile(idUser, userToken, namaUser, dateOfBirthUser);
        call.enqueue(new Callback<ResponseUserById>() {
            @Override
            public void onResponse(Call<ResponseUserById> call, Response<ResponseUserById> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseUserById> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getUserById(int idUser, GetUserByIdCallback callback) {
        Call<ResponseUserById> call = apiInterface.getUserById(idUser);
        call.enqueue(new Callback<ResponseUserById>() {
            @Override
            public void onResponse(Call<ResponseUserById> call, Response<ResponseUserById> response) {
                callback.onSuccess(response.body().getUser());
            }

            @Override
            public void onFailure(Call<ResponseUserById> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void resetUserPassword(String email, ResetUserPasswordCallback callback) {
        Call<ResponseForgotPassword> call = apiInterface.changePassword(email);
        call.enqueue(new Callback<ResponseForgotPassword>() {
            @Override
            public void onResponse(Call<ResponseForgotPassword> call, Response<ResponseForgotPassword> response) {
                if (response.code() == 200)
                    callback.onSuccess(response.body().getPesan(), response.code());
                else {
                    try {
                        JSONObject jsonObjectError = new JSONObject(response.errorBody().string());
                        callback.onSuccess(jsonObjectError.getString("message"), response.code());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseForgotPassword> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void registerUser(String nama, String email, Date dateOfBirth, String password, String confirmPassword, RegisterUserCallback callback) {
        Call<ResponseRegister> call = apiInterface.sendRegister(nama, email, dateOfBirth, password, confirmPassword);
        call.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                callback.onSuccess(response.body().getUser(), response.body().getAccess_token(), response.message());
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                callback.onFailure("Email sudah terdaftar sebagai akun Tracki");
            }
        });
    }

    @Override
    public void loginUser(String email, String password, LoginUserCallback callback) {
        Call<ResponseLogin> call = apiInterface.sendLogin(email, password);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.code() == 201){
                    callback.onSuccess(response.body().getUserLogin(), response.body().getAccessToken(), response.body().getMessage(), response.code());
                }
                else {
                    try {
                        callback.onSuccess(null, null, response.errorBody().string(), response.code());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void logoutUser(LogoutCallback callback) {
        Call<ResponseLogout> call = apiInterface.sendLogout();
        call.enqueue(new Callback<ResponseLogout>() {
            @Override
            public void onResponse(Call<ResponseLogout> call, Response<ResponseLogout> response) {
                callback.onSuccess(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<ResponseLogout> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}

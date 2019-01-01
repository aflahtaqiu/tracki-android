package com.example.aflah.tracki_master.Model.Response;

import com.example.aflah.tracki_master.Model.UserLogin;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRegister {

    @SerializedName("user")
    @Expose
    private UserLogin user;
    @SerializedName("access_token")
    @Expose
    private String access_token;


    public UserLogin getUser() {
        return user;
    }

    public void setUser(UserLogin user) {
        this.user = user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "ResponseRegister{" +
                "user=" + user +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}

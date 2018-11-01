package com.example.aflah.tracki_master.Model.Response;

import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.Model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTokoFavourite {

    @SerializedName("user")
    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.example.aflah.tracki_master.Model.Response;

import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseUserById {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("used_promotions")
    @Expose
    private List<Promotion> used_promotions;
    @SerializedName("unused_promotions")
    @Expose
    private List<Promotion> unused_promotions;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Promotion> getUsed_promotions() {
        return used_promotions;
    }

    public void setUsed_promotions(List<Promotion> used_promotions) {
        this.used_promotions = used_promotions;
    }

    public List<Promotion> getUnused_promotions() {
        return unused_promotions;
    }

    public void setUnused_promotions(List<Promotion> unused_promotions) {
        this.unused_promotions = unused_promotions;
    }
}

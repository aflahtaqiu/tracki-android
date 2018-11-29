package com.example.aflah.tracki_master.Model.Response;

import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseRedeemPromotion {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("promotions")
    @Expose
    private List<Promotion> promotions;
    @SerializedName("user")
    @Expose
    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}

package com.example.aflah.tracki_master.Model.Response;

import com.example.aflah.tracki_master.Model.Promotion;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePromotionById {

    @SerializedName("promotion")
    @Expose
    private Promotion promotion;

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}

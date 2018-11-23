package com.example.aflah.tracki_master.Model.Response;

import com.example.aflah.tracki_master.Model.Promotion;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePromotion {

    @SerializedName("promotions")
    @Expose
    private List<Promotion> promotions;

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}

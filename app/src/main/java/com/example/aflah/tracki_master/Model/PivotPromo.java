package com.example.aflah.tracki_master.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PivotPromo {

    @SerializedName("store_id")
    @Expose
    private int store_id;
    @SerializedName("promotion_id")
    @Expose
    private int promotion_id;

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(int promotion_id) {
        this.promotion_id = promotion_id;
    }
}

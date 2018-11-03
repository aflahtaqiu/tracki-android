package com.example.aflah.tracki_master.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PivotReview {

    @SerializedName("store_id")
    @Expose
    private int store_id;
    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("rating")
    @Expose
    private double rating;
    @SerializedName("description")
    @Expose
    private String description;

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.example.aflah.tracki_master.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Advertisement {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("store_id")
    @Expose
    private int store_id;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("store")
    private Store store;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "id=" + id +
                ", store_id=" + store_id +
                ", banner='" + banner + '\'' +
                ", store=" + store +
                '}';
    }
}

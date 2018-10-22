package com.example.aflah.tracki_master.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Galery {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("store_id")
    @Expose
    private int store_id;
    @SerializedName("picture")
    @Expose
    private String picture;

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Galery{" +
                "id=" + id +
                ", store_id=" + store_id +
                ", picture='" + picture + '\'' +
                '}';
    }
}

package com.example.aflah.tracki_master.Model.Response;

import com.example.aflah.tracki_master.Model.Store;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAddReview {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("store")
    @Expose
    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

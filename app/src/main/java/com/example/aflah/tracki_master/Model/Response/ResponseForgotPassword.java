package com.example.aflah.tracki_master.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseForgotPassword {

    @SerializedName("message")
    @Expose
    private String pesan;

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}

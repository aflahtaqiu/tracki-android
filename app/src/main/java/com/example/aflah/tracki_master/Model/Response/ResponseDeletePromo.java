package com.example.aflah.tracki_master.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDeletePromo {

    @SerializedName("message")
    @Expose
    private  String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseDeletePromo{" +
                "message='" + message + '\'' +
                '}';
    }
}

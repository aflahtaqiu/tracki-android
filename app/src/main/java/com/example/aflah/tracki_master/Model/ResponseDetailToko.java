package com.example.aflah.tracki_master.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDetailToko {

    @SerializedName("store")
    @Expose
    private DetailToko detailToko;

    public DetailToko getDetailToko() {
        return detailToko;
    }

    public void setDetailToko(DetailToko detailToko) {
        this.detailToko = detailToko;
    }
}

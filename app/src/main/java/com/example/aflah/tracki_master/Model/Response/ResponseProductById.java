package com.example.aflah.tracki_master.Model.Response;

import com.example.aflah.tracki_master.Model.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseProductById {

    @SerializedName("product")
    @Expose
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

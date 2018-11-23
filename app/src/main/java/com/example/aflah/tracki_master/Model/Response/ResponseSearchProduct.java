package com.example.aflah.tracki_master.Model.Response;

import com.example.aflah.tracki_master.Model.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ennobel on 11/20/2018.
 */

public class ResponseSearchProduct {
    @SerializedName("stores")
    @Expose

    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}

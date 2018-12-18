package com.example.aflah.tracki_master.Data;

import android.util.Log;

import com.example.aflah.tracki_master.Data.remote.ProductRemoteDataSource;
import com.example.aflah.tracki_master.Model.ResponseProductById;

public class ProductRepository implements ProductSource {

    private ProductRemoteDataSource productRemoteDataSource;

    public ProductRepository(ProductRemoteDataSource productRemoteDataSource) {
        this.productRemoteDataSource = productRemoteDataSource;
    }

    @Override
    public void getProductById(int id, GetProductByIdCallBack getProductByIdCallBack) {
        productRemoteDataSource.getProductById(id, new GetProductByIdCallBack() {
            @Override
            public void onSuccess(ResponseProductById product) {
                getProductByIdCallBack.onSuccess(product);
            }

            @Override
            public void onFailure(String errorMessage) {
                getProductByIdCallBack.onFailure(errorMessage);
            }
        });
    }
}

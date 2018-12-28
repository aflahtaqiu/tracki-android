package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Model.ResponseProductById;

public interface ProductSource {

    void getProductById(int id, GetProductByIdCallBack getProductByIdCallBack);

    interface GetProductByIdCallBack {

        void onSuccess(ResponseProductById product);

        void onFailure(String errorMessage);
    }

}

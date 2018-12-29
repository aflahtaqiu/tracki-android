package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.Model.ResponseProductById;

import java.util.List;

public interface ProductSource {

    void getProductById(int id, GetProductByIdCallBack getProductByIdCallBack);
    void getMakanan(int idToko, GetMakananCallback getMakananCallback);
    void getMinuman(int idToko, GetMinumanCallback getMinumanCallback);

    interface GetProductByIdCallBack {

        void onSuccess(ResponseProductById product);

        void onFailure(String errorMessage);
    }

    interface GetMakananCallback {
        void onSuccess(List<Product> listMakanan);
        void onFailure(String errMsg);
    }

    interface GetMinumanCallback {
        void onSuccess(List<Product> listMinuman);
        void onFailure(String errMsg);
    }

}

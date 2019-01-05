package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.Model.Response.ResponseProductById;
import com.example.aflah.tracki_master.Model.SearchName;

import java.util.List;

public interface ProductSource {

    void getProductById(int id, GetProductByIdCallBack getProductByIdCallBack);
    void getMakanan(int idToko, GetMakananCallback getMakananCallback);
    void getMinuman(int idToko, GetMinumanCallback getMinumanCallback);
    void getSearchList(String keyword , GetProductsSearchList getProductsSearchList);
    void getSearchProductByInput(GetSearchProductByInputCallback callback);

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
    interface GetProductsSearchList {
        void onSuccess(List<Product> listProduct);
        void onFailure(String errMsg);
    }

    interface GetSearchProductByInputCallback {
        void onSuccess(List<SearchName> searchNameProductList);
        void onFailure(String errMsg);
    }
}

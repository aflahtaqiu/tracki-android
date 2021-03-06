package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Data.remote.ProductRemoteDataSource;
import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.Model.Response.ResponseProductById;
import com.example.aflah.tracki_master.Model.SearchName;

import java.util.List;

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

    @Override
    public void getMakanan(int idToko, GetMakananCallback getMakananCallback) {
        productRemoteDataSource.getMakanan(idToko, new GetMakananCallback() {
            @Override
            public void onSuccess(List<Product> listMakanan) {
                getMakananCallback.onSuccess(listMakanan);
            }

            @Override
            public void onFailure(String errMsg) {
                getMakananCallback.onFailure(errMsg);
            }
        });
    }

    @Override
    public void getMinuman(int idToko, GetMinumanCallback getMinumanCallback) {
        productRemoteDataSource.getMinuman(idToko, new GetMinumanCallback() {
            @Override
            public void onSuccess(List<Product> listMinuman) {
                getMinumanCallback.onSuccess(listMinuman);
            }

            @Override
            public void onFailure(String errMsg) {
                getMinumanCallback.onFailure(errMsg);
            }
        });
    }

    @Override
    public void getSearchList(String keyword, GetProductsSearchList getProductsSearchList) {
        productRemoteDataSource.getSearchList(keyword, new GetProductsSearchList() {
            @Override
            public void onSuccess(List<Product> listProduct) {
                getProductsSearchList.onSuccess(listProduct);
            }

            @Override
            public void onFailure(String errMsg) {
                getProductsSearchList.onFailure(errMsg);
            }
        });
    }

    @Override
    public void getSearchProductByInput(GetSearchProductByInputCallback callback) {
        productRemoteDataSource.getSearchProductByInput(new GetSearchProductByInputCallback() {
            @Override
            public void onSuccess(List<SearchName> searchNameProductList) {
                callback.onSuccess(searchNameProductList);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }
}

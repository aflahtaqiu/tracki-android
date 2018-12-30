package com.example.aflah.tracki_master.Data.remote;

import com.example.aflah.tracki_master.Data.ProductSource;
import com.example.aflah.tracki_master.Data.remote.API.ApiClient;
import com.example.aflah.tracki_master.Data.remote.API.ApiInterface;
import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchProduct;
import com.example.aflah.tracki_master.Model.ResponseProductById;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRemoteDataSource implements ProductSource {

    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    public void getProductById(int id, GetProductByIdCallBack getProductByIdCallBack) {

        Call<ResponseProductById> call = apiInterface.getProductById(id);
        call.enqueue(new Callback<ResponseProductById>() {
            @Override
            public void onResponse(Call<ResponseProductById> call, Response<ResponseProductById> response) {
                getProductByIdCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseProductById> call, Throwable t) {
                getProductByIdCallBack.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getMakanan(int idToko, GetMakananCallback getMakananCallback) {
        Call<ResponseDetailToko> call = apiInterface.getStoreByID(idToko);
        call.enqueue(new Callback<ResponseDetailToko>() {
            @Override
            public void onResponse(Call<ResponseDetailToko> call, Response<ResponseDetailToko> response) {
                List<Product> listMakanan =new ArrayList<>();
                for (Product product : response.body().getStore().getProducts()){
                    if (product.getCategory().getId() == 1){
                        listMakanan.add(product);
                    }
                }
                getMakananCallback.onSuccess(listMakanan);
            }

            @Override
            public void onFailure(Call<ResponseDetailToko> call, Throwable t) {
                getMakananCallback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getMinuman(int idToko, GetMinumanCallback getMinumanCallback) {
        Call<ResponseDetailToko> call = apiInterface.getStoreByID(idToko);
        call.enqueue(new Callback<ResponseDetailToko>() {
            @Override
            public void onResponse(Call<ResponseDetailToko> call, Response<ResponseDetailToko> response) {
                List<Product> listMinuman = new ArrayList<>();
                for (Product product : response.body().getStore().getProducts()){
                    if (product.getCategory().getId() == 2){
                        listMinuman.add(product);
                    }
                }
                getMinumanCallback.onSuccess(listMinuman);
            }

            @Override
            public void onFailure(Call<ResponseDetailToko> call, Throwable t) {
                getMinumanCallback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getSearchList(String keyword, GetSearhList getSearhList) {
        Call<ResponseSearchProduct> call = apiInterface.getProductList(keyword);
        call.enqueue(new Callback<ResponseSearchProduct>() {
            @Override
            public void onResponse(Call<ResponseSearchProduct> call, Response<ResponseSearchProduct> response) {
                getSearhList.onSuccess(response.body().getProductList());
            }

            @Override
            public void onFailure(Call<ResponseSearchProduct> call, Throwable t) {
                getSearhList.onFailure(t.getMessage());
            }
        });
    }
}

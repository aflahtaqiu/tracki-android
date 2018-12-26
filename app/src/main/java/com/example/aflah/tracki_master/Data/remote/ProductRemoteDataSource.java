package com.example.aflah.tracki_master.Data.remote;

import com.example.aflah.tracki_master.Data.ProductSource;
import com.example.aflah.tracki_master.Model.ResponseProductById;

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
}

package com.example.aflah.tracki_master.Data.remote;

import com.example.aflah.tracki_master.Data.StoreSource;
import com.example.aflah.tracki_master.Data.remote.API.ApiClient;
import com.example.aflah.tracki_master.Data.remote.API.ApiInterface;
import com.example.aflah.tracki_master.Model.Galery;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreRemoteDataSource implements StoreSource {

    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    public void getStoreUrlImages(int idToko, GetStoreImagesUrlCallback callback) {
        List<String> urlImageList = new ArrayList<>();
        Call<ResponseDetailToko> call = apiInterface.getStoreByID(idToko);
        call.enqueue(new Callback<ResponseDetailToko>() {
            @Override
            public void onResponse(Call<ResponseDetailToko> call, Response<ResponseDetailToko> response) {
                for (Galery galery : response.body().getStore().getGalleries()){
                    urlImageList.add(galery.getPicture());
                }
                callback.onSuccess(urlImageList);
            }

            @Override
            public void onFailure(Call<ResponseDetailToko> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getStoreDetail(int idToko, GetStoreDetailCallback callback) {
        Call<ResponseDetailToko> call = apiInterface.getStoreByID(idToko);
        call.enqueue(new Callback<ResponseDetailToko>() {
            @Override
            public void onResponse(Call<ResponseDetailToko> call, Response<ResponseDetailToko> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseDetailToko> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}

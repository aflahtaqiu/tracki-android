package com.example.aflah.tracki_master.Data.remote;

import com.example.aflah.tracki_master.Data.StoreSource;
import com.example.aflah.tracki_master.Data.remote.API.ApiClient;
import com.example.aflah.tracki_master.Data.remote.API.ApiInterface;
import com.example.aflah.tracki_master.Model.Galery;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchNameStore;
import com.example.aflah.tracki_master.Model.Response.ResponseTokoByUID;

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

    @Override
    public void getSearchList(String keyword, GetStoreSearchList callback) {
        Call<ResponseTokoByUID> call = apiInterface.getStore(keyword);
        call.enqueue(new Callback<ResponseTokoByUID>() {
            @Override
            public void onResponse(Call<ResponseTokoByUID> call, Response<ResponseTokoByUID> response) {
                callback.onSuccess(response.body().getStores());
            }

            @Override
            public void onFailure(Call<ResponseTokoByUID> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getStoreByUID(int uid, GetStoreByUIDCallback callback) {
        Call<ResponseTokoByUID> call = apiInterface.getStoreByUID(uid);
        call.enqueue(new Callback<ResponseTokoByUID>() {
            @Override
            public void onResponse(Call<ResponseTokoByUID> call, Response<ResponseTokoByUID> response) {
                callback.onSuccess(response.body().getStores());
            }

            @Override
            public void onFailure(Call<ResponseTokoByUID> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getSearchStoreByInput(GetSearchStoreByInputCallback callback) {
        Call<ResponseSearchNameStore> call = apiInterface.getSearchNamesStore();
        call.enqueue(new Callback<ResponseSearchNameStore>() {
            @Override
            public void onResponse(Call<ResponseSearchNameStore> call, Response<ResponseSearchNameStore> response) {
                callback.onSuccess(response.body().getSearchNamesStore());
            }

            @Override
            public void onFailure(Call<ResponseSearchNameStore> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}

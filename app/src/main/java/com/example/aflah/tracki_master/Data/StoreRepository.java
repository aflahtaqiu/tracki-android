package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Data.remote.StoreRemoteDataSource;

import java.util.List;

public class StoreRepository implements StoreSource{

    private StoreRemoteDataSource storeRemoteDataSource;

    public StoreRepository(StoreRemoteDataSource storeRemoteDataSource) {
        this.storeRemoteDataSource = storeRemoteDataSource;
    }

    @Override
    public void getStoreUrlImages(int idToko, GetStoreImagesUrlCallback callback) {
        storeRemoteDataSource.getStoreUrlImages(idToko, new GetStoreImagesUrlCallback() {
            @Override
            public void onSuccess(List<String> imagesUrl) {
                callback.onSuccess(imagesUrl);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }
}

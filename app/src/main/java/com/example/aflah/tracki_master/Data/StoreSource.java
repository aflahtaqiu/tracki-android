package com.example.aflah.tracki_master.Data;

import java.util.List;

public interface StoreSource {

    void getStoreUrlImages(int idToko, GetStoreImagesUrlCallback callback);

    interface GetStoreImagesUrlCallback {

        void onSuccess(List<String> imagesUrl);
        void onFailure(String errMsg);
    }
}

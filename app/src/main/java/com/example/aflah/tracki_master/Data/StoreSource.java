package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;

import java.util.List;

public interface StoreSource {

    void getStoreUrlImages(int idToko, GetStoreImagesUrlCallback callback);
    void getStoreDetail(int idToko, GetStoreDetailCallback callback);

    interface GetStoreImagesUrlCallback {
        void onSuccess(List<String> imagesUrl);
        void onFailure(String errMsg);
    }

    interface GetStoreDetailCallback {
        void onSuccess(ResponseDetailToko responseDetailToko);
        void onFailure(String errMsg);
    }
}

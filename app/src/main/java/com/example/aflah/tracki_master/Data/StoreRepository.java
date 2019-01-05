package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Data.remote.StoreRemoteDataSource;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.Model.Store;

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

    @Override
    public void getStoreDetail(int idToko, GetStoreDetailCallback callback) {
        storeRemoteDataSource.getStoreDetail(idToko, new GetStoreDetailCallback() {
            @Override
            public void onSuccess(ResponseDetailToko responseDetailToko) {
                callback.onSuccess(responseDetailToko);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }

    @Override
    public void getSearchList(String keyword, GetStoreSearchList callback) {
        storeRemoteDataSource.getSearchList(keyword, new GetStoreSearchList() {
            @Override
            public void onSuccess(List<Store> listStore) {
                callback.onSuccess(listStore);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }

    @Override
    public void getStoreByUID(int uid, GetStoreByUIDCallback callback) {
        storeRemoteDataSource.getStoreByUID(uid, new GetStoreByUIDCallback() {
            @Override
            public void onSuccess(List<Store> storeList) {
                callback.onSuccess(storeList);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }
}

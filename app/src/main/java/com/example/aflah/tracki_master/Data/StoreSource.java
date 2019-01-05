package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.Model.SearchName;
import com.example.aflah.tracki_master.Model.Store;

import java.util.List;

public interface StoreSource {

    void getStoreUrlImages(int idToko, GetStoreImagesUrlCallback callback);
    void getStoreDetail(int idToko, GetStoreDetailCallback callback);
    void getSearchList(String keyword, GetStoreSearchList callback);
    void getStoreByUID(int uid, GetStoreByUIDCallback callback);
    void getSearchStoreByInput(GetSearchStoreByInputCallback callback);

    interface GetStoreImagesUrlCallback {
        void onSuccess(List<String> imagesUrl);
        void onFailure(String errMsg);
    }

    interface GetStoreDetailCallback {
        void onSuccess(ResponseDetailToko responseDetailToko);
        void onFailure(String errMsg);
    }

    interface GetStoreSearchList {
        void onSuccess(List<Store> listStore);
        void onFailure(String errMsg);
    }

    interface GetStoreByUIDCallback {
        void onSuccess(List<Store> storeList);
        void onFailure(String errMsg);
    }

    interface GetSearchStoreByInputCallback {
        void onSuccess(List<SearchName> searchNameStoreList);
        void onFailure(String errMsg);
    }
}

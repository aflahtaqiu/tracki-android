package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.SearchName;
import com.example.aflah.tracki_master.Model.Store;

import java.util.List;

public interface HomeContract {

    interface presenter {
        void getNearestStore(int uid);
        void getSearchStoreByInput();
        void getSearchProductByInput();
    }

    interface view {
        void showNearestStore(List<Store> storeList);
        void showFailure(String errMsg);
        void showAutoCompleteText(String[] arraysOfName, List<SearchName> searchNameList);
    }
}

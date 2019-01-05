package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.Store;

import java.util.List;

public interface HomeContract {

    interface presenter {
        void getNearestStore(int uid);
    }

    interface view {
        void showNearestStore(List<Store> storeList);
        void showFailure(String errMsg);
    }
}

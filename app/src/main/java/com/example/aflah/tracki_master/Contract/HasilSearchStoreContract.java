package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.Store;

import java.util.List;

public interface HasilSearchStoreContract {

    interface presenter {
       void getListStore(String keyword);
    }
    interface view{
        void showDataList(List<Store> listStore);
        void showFailureMessage(String errorMessage);
    }
}

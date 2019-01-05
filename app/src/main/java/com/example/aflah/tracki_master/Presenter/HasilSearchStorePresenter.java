package com.example.aflah.tracki_master.Presenter;

import com.example.aflah.tracki_master.Contract.HasilSearchStoreContract;
import com.example.aflah.tracki_master.Data.StoreRepository;
import com.example.aflah.tracki_master.Data.StoreSource;
import com.example.aflah.tracki_master.Model.Store;

import java.util.List;

public class HasilSearchStorePresenter implements HasilSearchStoreContract.presenter{
    private StoreRepository storeRepository;
    private HasilSearchStoreContract.view view;

    public HasilSearchStorePresenter(StoreRepository storeRepository,HasilSearchStoreContract.view view){
        this.storeRepository = storeRepository;
        this.view = view;
    }

    @Override
    public void getListStore(String keyword) {
        storeRepository.getSearchList(keyword, new StoreSource.GetStoreSearchList() {
            @Override
            public void onSuccess(List<Store> listStore) {
                view.showDataList(listStore);
            }

            @Override
            public void onFailure(String errMsg) {
                view.showFailureMessage(errMsg);
            }
        });
    }
}

package com.example.aflah.tracki_master.Presenter;

import com.example.aflah.tracki_master.Contract.HomeContract;
import com.example.aflah.tracki_master.Data.ProductRepository;
import com.example.aflah.tracki_master.Data.StoreRepository;
import com.example.aflah.tracki_master.Data.StoreSource;
import com.example.aflah.tracki_master.Model.Store;

import java.util.List;

public class HomePresenter implements HomeContract.presenter {

    private StoreRepository storeRepository;
    private ProductRepository productRepository;
    private HomeContract.view view;

    public HomePresenter(StoreRepository storeRepository, ProductRepository productRepository, HomeContract.view view) {
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.view = view;
    }

    @Override
    public void getNearestStore(int uid) {
        storeRepository.getStoreByUID(uid, new StoreSource.GetStoreByUIDCallback() {
            @Override
            public void onSuccess(List<Store> storeList) {
                if (!storeList.isEmpty())
                    view.showNearestStore(storeList);
                else view.showIsNoNearestStore();
            }

            @Override
            public void onFailure(String errMsg) {
                view.showFailure(errMsg);
            }
        });
    }
}

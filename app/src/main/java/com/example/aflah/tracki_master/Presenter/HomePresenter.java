package com.example.aflah.tracki_master.Presenter;

import com.example.aflah.tracki_master.Contract.HomeContract;
import com.example.aflah.tracki_master.Data.ProductRepository;
import com.example.aflah.tracki_master.Data.ProductSource;
import com.example.aflah.tracki_master.Data.StoreRepository;
import com.example.aflah.tracki_master.Data.StoreSource;
import com.example.aflah.tracki_master.Model.SearchName;
import com.example.aflah.tracki_master.Model.Store;

import java.util.ArrayList;
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
            }

            @Override
            public void onFailure(String errMsg) {
                view.showFailure(errMsg);
            }
        });
    }

    @Override
    public void getSearchStoreByInput() {
        storeRepository.getSearchStoreByInput(new StoreSource.GetSearchStoreByInputCallback() {
            @Override
            public void onSuccess(List<SearchName> searchNameStoreList) {
                List<SearchName> searchNameList = new ArrayList<>();
                String[] namaToko = new String[searchNameStoreList.size()];
                for (int i = 0; i< searchNameStoreList.size();i++){
                    searchNameList.add(searchNameStoreList.get(i));
                    namaToko[i] = searchNameStoreList.get(i).getName();
                }
                view.showAutoCompleteText(namaToko, searchNameList);
            }

            @Override
            public void onFailure(String errMsg) {
                view.showFailure(errMsg);
            }
        });
    }

    @Override
    public void getSearchProductByInput() {
        productRepository.getSearchProductByInput(new ProductSource.GetSearchProductByInputCallback() {
            @Override
            public void onSuccess(List<SearchName> searchNameProductList) {
                List<SearchName> searchNameList = new ArrayList<>();
                String[] namaProduk = new String[searchNameProductList.size()];
                for (int i =0;i<searchNameProductList.size();i++){
                    searchNameList.add(searchNameProductList.get(i));
                    namaProduk[i] = searchNameProductList.get(i).getName();
                }
                view.showAutoCompleteText(namaProduk, searchNameList);
            }

            @Override
            public void onFailure(String errMsg) {
                view.showFailure(errMsg);
            }
        });
    }
}

package com.example.aflah.tracki_master.Presenter;

import android.util.Log;

import com.example.aflah.tracki_master.Contract.HomeContract;
import com.example.aflah.tracki_master.Data.ProductRepository;
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
//                List<String> namaTokoList = new ArrayList<>();
                List<SearchName> searchNameList = new ArrayList<>();
                String[] namaToko = new String[searchNameStoreList.size()];

                for (int i = 0; i< searchNameStoreList.size();i++){
                    Log.e("tesNamaToko", searchNameStoreList.get(i).toString());

//                    namaTokoList.add(i, searchNameStoreList.get(i).getName());
                    searchNameList.add(searchNameStoreList.get(i));
                    namaToko[i] = searchNameStoreList.get(i).getName();
                }

                view.showAutoCompleteTextStore(namaToko, searchNameList);
            }

            @Override
            public void onFailure(String errMsg) {

            }
        });
    }
}

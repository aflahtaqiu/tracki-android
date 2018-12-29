package com.example.aflah.tracki_master.Presenter;

import com.example.aflah.tracki_master.Contract.TokoContract;
import com.example.aflah.tracki_master.Data.StoreRepository;
import com.example.aflah.tracki_master.Data.StoreSource;

import java.util.List;

public class TokoPresenter implements TokoContract.presenter {

    private StoreRepository storeRepository;
    private TokoContract.view view;

    public TokoPresenter(StoreRepository storeRepository, TokoContract.view view) {
        this.storeRepository = storeRepository;
        this.view = view;
    }

    @Override
    public void getImagesUrl(int idToko) {
        storeRepository.getStoreUrlImages(idToko, new StoreSource.GetStoreImagesUrlCallback() {
            @Override
            public void onSuccess(List<String> imagesUrl) {
                view.showDataList(imagesUrl);
            }

            @Override
            public void onFailure(String errMsg) {

            }
        });

        view.showViewPager();
    }
}

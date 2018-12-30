package com.example.aflah.tracki_master.Presenter;

import com.example.aflah.tracki_master.Contract.DetailTokoContract;
import com.example.aflah.tracki_master.Data.StoreRepository;
import com.example.aflah.tracki_master.Data.StoreSource;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;

public class DetailTokoPresenter implements DetailTokoContract.presenter {

    private StoreRepository storeRepository;
    private DetailTokoContract.view view;

    public DetailTokoPresenter(StoreRepository storeRepository, DetailTokoContract.view view) {
        this.storeRepository = storeRepository;
        this.view = view;
    }

    @Override
    public void getStoreDetail(int idToko) {
        view.showProgress();
        storeRepository.getStoreDetail(idToko, new StoreSource.GetStoreDetailCallback() {
            @Override
            public void onSuccess(ResponseDetailToko responseDetailToko) {
                view.hideProgress();
                view.showDataList(responseDetailToko);
            }

            @Override
            public void onFailure(String errMsg) {
                view.hideProgress();
                view.showError(errMsg);
            }
        });
    }
}

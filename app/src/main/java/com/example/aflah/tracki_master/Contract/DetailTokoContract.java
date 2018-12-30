package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;

public interface DetailTokoContract {

    interface presenter {
        void getStoreDetail(int idToko);
    }

    interface view {
        void showProgress();
        void hideProgress();
        void showDataList(ResponseDetailToko responseDetailToko);
        void showError(String errMsg);
    }
}

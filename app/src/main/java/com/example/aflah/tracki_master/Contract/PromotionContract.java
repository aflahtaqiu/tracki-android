package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.Promotion;

import java.util.List;

public interface PromotionContract {

    interface view{
        void showProgress();
        void hideProgress();
        void showDataList(List<Promotion> promotionList);
        void showFailureMessage(String errorMessage);
    }

    interface presenter{
        void getListDataPromotions();
    }
}

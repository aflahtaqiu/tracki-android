package com.example.aflah.tracki_master.NavbarFragment.Promotion;

import com.example.aflah.tracki_master.Data.PromotionRepository;
import com.example.aflah.tracki_master.Data.PromotionSource;
import com.example.aflah.tracki_master.Model.Promotion;

import java.util.List;

public class PromotionPresenter implements PromotionContract.presenter {

    private PromotionRepository promotionRepository;
    private PromotionContract.view view;

    public PromotionPresenter(PromotionRepository promotionRepository, PromotionContract.view view) {
        this.promotionRepository = promotionRepository;
        this.view = view;
    }

    @Override
    public void getListDataPromotions() {

        view.showProgress();
        promotionRepository.getAllPromotion(new PromotionSource.GetPromotionCallback() {
            @Override
            public void onSuccess(List<Promotion> promotionList) {
                view.hideProgress();
                view.showDataList(promotionList);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideProgress();
                view.showFailureMessage(errorMessage);
            }
        });
    }
}

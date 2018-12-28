package com.example.aflah.tracki_master.DetailPromo;

import com.example.aflah.tracki_master.Data.DetailPromoRepository;
import com.example.aflah.tracki_master.Data.DetailPromoSource;
import com.example.aflah.tracki_master.Model.Promotion;

public class DetailPromoPresenter implements DetailPromoContract.presenter {

    private DetailPromoRepository detailPromoRepository;
    private DetailPromoContract.view view;

    public DetailPromoPresenter(DetailPromoRepository detailPromoRepository, DetailPromoContract.view view) {
        this.detailPromoRepository = detailPromoRepository;
        this.view = view;
    }

    @Override
    public void getDetailPromo(String userToken, int idPromo) {
        view.showProgress();
        detailPromoRepository.getPromotionById(userToken, idPromo, new DetailPromoSource.GetPromotionByIdCallback() {
            @Override
            public void onSuccess(Promotion promotion) {
                view.hideProgress();
                view.showData(promotion);
                if (promotion.getUsed() == true){
                    view.showIsPromoUsed();
                }
            }

            @Override
            public void onFailure(String errMsg) {

            }
        });
    }

    @Override
    public void sendRedeemData(String userToken, int idPromo) {

        detailPromoRepository.sendRedeemData(userToken, idPromo, new DetailPromoSource.SendRedeemDataCallback() {
            @Override
            public void onSuccess() {
                view.showDialogSuccess();
            }

            @Override
            public void onFailure(String errMsg) {
                view.showError(errMsg);
            }
        });
    }

    @Override
    public void generateQRCode() {
        view.showDialogQRCode();
    }
}

package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Data.remote.DetailPromoRemoteDataSource;
import com.example.aflah.tracki_master.Model.Promotion;

public class DetailPromoRepository implements DetailPromoSource {

    private DetailPromoRemoteDataSource detailPromoRemoteDataSource;

    public DetailPromoRepository(DetailPromoRemoteDataSource detailPromoRemoteDataSource) {
        this.detailPromoRemoteDataSource = detailPromoRemoteDataSource;
    }

    @Override
    public void getPromotionById(String userToken, int idPromo, GetPromotionByIdCallback callback) {
        detailPromoRemoteDataSource.getPromotionById(userToken, idPromo, new GetPromotionByIdCallback() {
            @Override
            public void onSuccess(Promotion promotion) {
                callback.onSuccess(promotion);
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }

    @Override
    public void sendRedeemData(String userToken, int idPromo, SendRedeemDataCallback callback) {
        detailPromoRemoteDataSource.sendRedeemData(userToken, idPromo, new SendRedeemDataCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onFailure(String errMsg) {
                callback.onFailure(errMsg);
            }
        });
    }
}

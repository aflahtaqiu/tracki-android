package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Model.Promotion;

public interface DetailPromoSource {

    void getPromotionById(String userToken, int idPromo, GetPromotionByIdCallback callback);
    void sendRedeemData(String userToken, int idPromo, SendRedeemDataCallback callback);

    interface GetPromotionByIdCallback{
        void onSuccess(Promotion promotion);
        void onFailure(String errMsg);
    }

    interface SendRedeemDataCallback {
        void onSuccess();
        void onFailure(String errMsg);
    }
}

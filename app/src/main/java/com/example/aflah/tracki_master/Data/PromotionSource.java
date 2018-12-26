package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.Response.ResponseRedeemPromotion;

import java.util.List;

public interface PromotionSource {

    void getAllPromotion(GetPromotionCallback callback);

    interface GetPromotionCallback{

        void onSuccess(List<Promotion> promotionList);

        void onFailure(String errorMessage);
    }
}

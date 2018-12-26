package com.example.aflah.tracki_master.Data;

import com.example.aflah.tracki_master.Data.remote.PromotionRemoteDataSource;
import com.example.aflah.tracki_master.Model.Promotion;

import java.util.List;

public class PromotionRepository implements PromotionSource {

    private PromotionRemoteDataSource promotionRemoteDataSource;

    public PromotionRepository(PromotionRemoteDataSource promotionRemoteDataSource) {
        this.promotionRemoteDataSource = promotionRemoteDataSource;
    }

    @Override
    public void getAllPromotion(GetPromotionCallback callback) {
        promotionRemoteDataSource.getAllPromotion(new GetPromotionCallback() {
            @Override
            public void onSuccess(List<Promotion> promotionList) {
                callback.onSuccess(promotionList);
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }
}

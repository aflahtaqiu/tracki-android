package com.example.aflah.tracki_master.Data.remote;

import com.example.aflah.tracki_master.Data.PromotionSource;
import com.example.aflah.tracki_master.Data.remote.API.ApiClient;
import com.example.aflah.tracki_master.Data.remote.API.ApiInterface;
import com.example.aflah.tracki_master.Model.Response.ResponseRedeemPromotion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromotionRemoteDataSource implements PromotionSource {

    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    public void getAllPromotion(GetPromotionCallback callback) {

        Call<ResponseRedeemPromotion> call = apiInterface.getPromotions();
        call.enqueue(new Callback<ResponseRedeemPromotion>() {
            @Override
            public void onResponse(Call<ResponseRedeemPromotion> call, Response<ResponseRedeemPromotion> response) {
                callback.onSuccess(response.body().getPromotions());
            }

            @Override
            public void onFailure(Call<ResponseRedeemPromotion> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}

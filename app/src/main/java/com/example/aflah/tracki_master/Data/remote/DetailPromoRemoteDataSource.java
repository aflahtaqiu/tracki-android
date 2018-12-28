package com.example.aflah.tracki_master.Data.remote;

import com.example.aflah.tracki_master.Data.DetailPromoSource;
import com.example.aflah.tracki_master.Data.remote.API.ApiClient;
import com.example.aflah.tracki_master.Data.remote.API.ApiInterface;
import com.example.aflah.tracki_master.Model.Response.ResponsePromotionById;
import com.example.aflah.tracki_master.Model.Response.ResponseRedeemPromotion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPromoRemoteDataSource implements DetailPromoSource {

    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    public void getPromotionById(String userToken, int idPromo, GetPromotionByIdCallback callback) {
        Call<ResponsePromotionById> call = apiInterface.getPromotionById(userToken, idPromo);
        call.enqueue(new Callback<ResponsePromotionById>() {
            @Override
            public void onResponse(Call<ResponsePromotionById> call, Response<ResponsePromotionById> response) {
                callback.onSuccess(response.body().getPromotion());
            }

            @Override
            public void onFailure(Call<ResponsePromotionById> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void sendRedeemData(String userToken, int idPromo, SendRedeemDataCallback callback) {
        Call<ResponseRedeemPromotion> call = apiInterface.sendRedeemData(userToken, idPromo);
        call.enqueue(new Callback<ResponseRedeemPromotion>() {
            @Override
            public void onResponse(Call<ResponseRedeemPromotion> call, Response<ResponseRedeemPromotion> response) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(Call<ResponseRedeemPromotion> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}

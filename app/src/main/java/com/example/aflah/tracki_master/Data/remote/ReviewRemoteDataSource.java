package com.example.aflah.tracki_master.Data.remote;

import com.example.aflah.tracki_master.Data.ReviewTokoSource;
import com.example.aflah.tracki_master.Model.Response.ResponseAddReview;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRemoteDataSource implements ReviewTokoSource {

    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    public void getReview(int idStore, GetReviewByStoreCallback callback) {
        Call<ResponseDetailToko> call = apiInterface.getStoreByID(idStore);
        call.enqueue(new Callback<ResponseDetailToko>() {
            @Override
            public void onResponse(Call<ResponseDetailToko> call, Response<ResponseDetailToko> response) {
                callback.onSuccess(response.body().getStore());
            }

            @Override
            public void onFailure(Call<ResponseDetailToko> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void sendReview(String userToken, int idToko, double ratingToko, String komentarToko, SendReviewByStoreCallback callback) {
        Call<ResponseAddReview> call = apiInterface.addReview(userToken, idToko, ratingToko, komentarToko);
        call.enqueue(new Callback<ResponseAddReview>() {
            @Override
            public void onResponse(Call<ResponseAddReview> call, Response<ResponseAddReview> response) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(Call<ResponseAddReview> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}

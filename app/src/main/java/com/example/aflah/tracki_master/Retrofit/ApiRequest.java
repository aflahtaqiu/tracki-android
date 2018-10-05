package com.example.aflah.tracki_master.Retrofit;

import com.example.aflah.tracki_master.Model.ResponseDetailToko;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequest {

    @GET("store/3")
    Call<ResponseDetailToko> getStore();
}

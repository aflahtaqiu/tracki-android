package com.example.aflah.tracki_master.Retrofit;

import com.example.aflah.tracki_master.Model.StoreResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestData {

    @GET("/api/v1/store")
    Call<StoreResponse> getStores();
}

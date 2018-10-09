package com.example.aflah.tracki_master.Retrofit;

import com.example.aflah.tracki_master.Model.Advertisements;
import com.example.aflah.tracki_master.Model.ResponseDetailToko;
import com.example.aflah.tracki_master.Model.ResponseTokoTerdekat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiRequest {

    @GET("store/uid/{numberUID}")
    Call<ResponseTokoTerdekat> getStoreByUID(
        @Path("numberUID") String numberUID
    );

    @GET("store/{id}")
    Call<ResponseDetailToko> getStoreByID(
            @Path("id") int id
    );

    @GET("advertisement")
    Call<Advertisements> getAdvertisements();
}

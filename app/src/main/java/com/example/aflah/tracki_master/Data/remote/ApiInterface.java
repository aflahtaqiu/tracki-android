package com.example.aflah.tracki_master.Data.remote;

import com.example.aflah.tracki_master.Model.ResponseProductById;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("product/{id}")
    Call<ResponseProductById> getProductById(
            @Path("id") int id
    );
}

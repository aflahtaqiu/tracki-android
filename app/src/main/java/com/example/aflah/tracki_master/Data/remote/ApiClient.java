package com.example.aflah.tracki_master.Data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://still-spire-77258.herokuapp.com/api/v1/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){

        retrofit  = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}

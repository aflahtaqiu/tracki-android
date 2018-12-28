package com.example.aflah.tracki_master.Data.remote.API;

import com.example.aflah.tracki_master.Model.Response.ResponseAddReview;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.Model.Response.ResponsePromotionById;
import com.example.aflah.tracki_master.Model.Response.ResponseRedeemPromotion;
import com.example.aflah.tracki_master.Model.ResponseProductById;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("product/{id}")
    Call<ResponseProductById> getProductById(
            @Path("id") int id
    );

    @GET("promotion")
    Call<ResponseRedeemPromotion> getPromotions();

    @GET("store/{id}")
    Call<ResponseDetailToko> getStoreByID(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("review")
    Call<ResponseAddReview> addReview(
            @Header("Authorization") String token,
            @Field("store_id") int store_id,
            @Field("rating") double rating,
            @Field("description") String description
    );

    @GET("promotion/{id}")
    Call<ResponsePromotionById> getPromotionById(
            @Header("Authorization") String token,
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("redeem")
    Call<ResponseRedeemPromotion> sendRedeemData (
            @Header("Authorization") String token,
            @Field("promotion_id") int promotion_id
    );
}

package com.example.aflah.tracki_master.Retrofit;

import com.example.aflah.tracki_master.Model.Advertisements;
import com.example.aflah.tracki_master.Model.Response.ResponseAddReview;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.Model.Response.ResponseLogin;
import com.example.aflah.tracki_master.Model.Response.ResponsePromotion;
import com.example.aflah.tracki_master.Model.Response.ResponsePromotionById;
import com.example.aflah.tracki_master.Model.Response.ResponseRegister;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchNameProduct;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchNameStore;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchProduct;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.Response.ResponseTokoByUID;

import java.io.File;
import java.util.Date;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("store/uid/{numberUID}")
    Call<ResponseTokoByUID> getStoreByUID(
        @Path("numberUID") int numberUID
    );

    @GET("store/{id}")
    Call<ResponseDetailToko> getStoreByID(
            @Path("id") int id
    );

    @GET("user/{id}")
    Call<ResponseUserById> getTokoFavorit(
            @Path("id") int id
    );

    @GET("search-product")
    Call<ResponseSearchProduct> getProductList(@Query("name") String name);

    @GET("search-store")
    Call<ResponseTokoByUID> getStore(@Query("name") String name);

    @GET("advertisement")
    Call<Advertisements> getAdvertisements();

    @GET("promotion")
    Call<ResponsePromotion> getPromotions();

    @GET("promotion/{id}")
    Call<ResponsePromotionById> getPromotionById(
            @Path("id") int id
    );

    @GET("all-stores")
    Call<ResponseSearchNameStore> getSearchNamesStore();

    @GET("all-products")
    Call<ResponseSearchNameProduct> getSearchNamesProduct();

    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseRegister> sendRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("date_of_birth") Date dateOfBirth,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );

    @FormUrlEncoded
    @POST("redeem")
    Call<ResponsePromotion> getRedeemData (
            @Field("promotion_id") int promotion_id
    );

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseLogin> sendLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @PATCH("user/{id}")
    Call<ResponseUserById> updateProfile(
            @Path("id") int idUser,
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("date_of_birth") Date dateOfBirth
    );

    @FormUrlEncoded
    @Multipart
    @PUT("user/photo/{id}")
    Call<ResponseUserById> updateProfilPicture(
            @Path("id") int idUser,
            @Header("Authorization") String token,
            @Part MultipartBody.Part file
            );



    @FormUrlEncoded
    @POST("review")
    Call<ResponseAddReview> addReview(
            @Header("Authorization") String token,
            @Field("store_id") int store_id,
            @Field("rating") double rating,
            @Field("description") String description
    );

}

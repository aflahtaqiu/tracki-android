package com.example.aflah.tracki_master.Retrofit;

import com.example.aflah.tracki_master.Model.Advertisements;
import com.example.aflah.tracki_master.Model.Response.ResponseAddReview;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.Model.Response.ResponseLogin;
import com.example.aflah.tracki_master.Model.Response.ResponseRegister;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.Response.ResponseTokoTerdekat;

import java.util.Date;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("user/{id}")
    Call<ResponseUserById> getTokoFavorit(
            @Path("id") int id
    );

    @GET("advertisement")
    Call<Advertisements> getAdvertisements();

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
    @POST("user/login")
    Call<ResponseLogin> sendLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @PATCH("user/{id}")
    Call<ResponseUserById> updateProfile(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Field("name") String nama,
            @Field("date_of_birth") Date dateOfBirth
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
